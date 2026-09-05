package edu.project.dlearn.presentation.enseignant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.model.UniteApprentissage
import edu.project.dlearn.domain.usecase.AssignerContenuUseCase
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetElevesUseCase
import edu.project.dlearn.domain.usecase.GetProductionsSoumisesUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnseignantViewModel @Inject constructor(
    private val getAllUnites: GetAllUnitesUseCase,
    private val getEleves: GetElevesUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase,
    private val assignerContenu: AssignerContenuUseCase,
    private val getProductionsSoumises: GetProductionsSoumisesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnseignantUiState())
    val uiState: StateFlow<EnseignantUiState> = _uiState.asStateFlow()

    /** Cache local des unités pour retrouver le titre lors de l'affichage des corrections. */
    private var unitesParId: Map<String, UniteApprentissage> = emptyMap()
    private var elevesParId: Map<Long, EleveResume> = emptyMap()

    init { charger() }

    private fun charger() {
        viewModelScope.launch {
            val enseignant = getUtilisateurConnecte()
            val unites = getAllUnites().first()
            unitesParId = unites.associateBy { it.id }

            combine(getEleves(), getProductionsSoumises()) { utilisateurs, productions ->
                val elevesResume = utilisateurs.map { u ->
                    EleveResume(
                        id = u.id, nomAffiche = u.nomAffiche, classe = u.classe, niveauGer = u.niveau,
                        unitesTerminees = 0, scoreMoyen = 0
                    )
                }
                elevesParId = elevesResume.associateBy { it.id }

                val productionsResume = productions.mapNotNull { p ->
                    val eleve = elevesParId[p.eleveId] ?: return@mapNotNull null
                    val unite = unitesParId[p.uniteId]
                    ProductionACorriger(
                        productionId     = p.id,
                        eleveNom         = eleve.nomAffiche,
                        uniteTitre       = unite?.titre ?: p.uniteId,
                        extrait          = p.contenuTexte.take(140) + if (p.contenuTexte.length > 140) "…" else "",
                        dateModification = p.dateModification
                    )
                }.sortedByDescending { it.dateModification }

                elevesResume to productionsResume
            }.collect { (elevesResume, productionsResume) ->
                _uiState.update { it.copy(
                    enseignantId       = enseignant?.id ?: 0L,
                    enseignantNom      = enseignant?.nomAffiche ?: "Enseignant",
                    unitesDisponibles  = unites,
                    eleves             = elevesResume,
                    productionsACorriger = productionsResume,
                    enChargement       = false
                )}
            }
        }
    }

    fun onChangerOnglet(onglet: OngletEnseignant) {
        _uiState.update { it.copy(ongletActif = onglet) }
    }

    /**
     * cibleType : "ELEVE" (cibleId = id élève en String) ou "CLASSE" (cibleId = nom de classe).
     * En mode ELEVE, appeler une fois par élève sélectionné.
     */
    fun onAssigner(uniteId: String, cibleType: String, cibleId: String) {
        viewModelScope.launch {
            val enseignantId = _uiState.value.enseignantId
            if (enseignantId == 0L) return@launch
            assignerContenu(enseignantId, cibleType, cibleId, uniteId)
        }
    }
}
