package edu.project.dlearn.presentation.enseignant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.model.UniteApprentissage
import edu.project.dlearn.domain.usecase.AssignerContenuUseCase
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetElevesUseCase
import edu.project.dlearn.domain.usecase.GetProductionsSoumisesUseCase
import edu.project.dlearn.domain.usecase.GetProgressionStatsUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import edu.project.dlearn.domain.usecase.ImportDataUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface EnseignantEvenement {
    data class ImportReussi(val resume: ImportUiResume) : EnseignantEvenement
    data class ImportEchoue(val message: String) : EnseignantEvenement
}

@HiltViewModel
class EnseignantViewModel @Inject constructor(
    private val getAllUnites: GetAllUnitesUseCase,
    private val getEleves: GetElevesUseCase,
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase,
    private val assignerContenu: AssignerContenuUseCase,
    private val getProductionsSoumises: GetProductionsSoumisesUseCase,
    private val getProgressionStats: GetProgressionStatsUseCase,
    private val importDataUseCase: ImportDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnseignantUiState())
    val uiState: StateFlow<EnseignantUiState> = _uiState.asStateFlow()

    private val _evenements = MutableSharedFlow<EnseignantEvenement>()
    val evenements: SharedFlow<EnseignantEvenement> = _evenements

    private var unitesParId: Map<String, UniteApprentissage> = emptyMap()
    private var elevesParId: Map<Long, EleveResume> = emptyMap()

    init { charger() }

    private fun charger() {
        viewModelScope.launch {
            val enseignant = getUtilisateurConnecte()
            val unites = getAllUnites().first()
            unitesParId = unites.associateBy { it.id }

            combine(getEleves(), getProductionsSoumises()) { utilisateurs, productions ->
                utilisateurs to productions
            }.collectLatest { (utilisateurs, productions) ->
                val elevesResume = utilisateurs.map { u ->
                    val stats = getProgressionStats(u.id).first()
                    EleveResume(
                        id              = u.id,
                        nomAffiche      = u.nomAffiche,
                        classe          = u.classe,
                        niveauGer       = u.niveau,
                        unitesTerminees = stats.unitesTerminees,
                        scoreMoyen      = stats.tauxReussite
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

                _uiState.update { it.copy(
                    enseignantId         = enseignant?.id ?: 0L,
                    enseignantNom        = enseignant?.nomAffiche ?: "Enseignant",
                    unitesDisponibles    = unites,
                    eleves               = elevesResume,
                    productionsACorriger = productionsResume,
                    enChargement         = false
                )}
            }
        }
    }

    fun onChangerOnglet(onglet: OngletEnseignant) {
        _uiState.update { it.copy(ongletActif = onglet) }
    }

    fun onAssigner(uniteId: String, cibleType: String, cibleId: String) {
        viewModelScope.launch {
            val enseignantId = _uiState.value.enseignantId
            if (enseignantId == 0L) return@launch
            assignerContenu(enseignantId, cibleType, cibleId, uniteId)
        }
    }

    fun onImporterFichier(uriString: String) {
        viewModelScope.launch {
            importDataUseCase(uriString)
                .onSuccess { resume ->
                    _evenements.emit(
                        EnseignantEvenement.ImportReussi(
                            ImportUiResume(
                                progressionsMisesAJour = resume.progressionsMisesAJour,
                                productionsMisesAJour  = resume.productionsMisesAJour,
                                ignorees = resume.progressionsIgnorees + resume.productionsIgnorees
                            )
                        )
                    )
                }
                .onFailure { e ->
                    _evenements.emit(EnseignantEvenement.ImportEchoue(e.message ?: "Erreur inconnue"))
                }
        }
    }
}
