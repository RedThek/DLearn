package edu.project.dlearn.presentation.accueil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.core.AppConstants
import edu.project.dlearn.domain.usecase.GetAllUnitesUseCase
import edu.project.dlearn.domain.usecase.GetAssignationsPourEleveUseCase
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Mission B1 (suite) : remplacer les données mockées de progression/streak/temps par les vraies
// données Room (GetProgressionStatsUseCase, désormais fiable depuis le correctif B-28) — reste hors
// scope strict d'AN-B3-01, à traiter dans une itération suivante de la Mission B1.
@HiltViewModel
class AccueilViewModel @Inject constructor(
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase,
    private val getAssignationsPourEleve: GetAssignationsPourEleveUseCase,
    private val getAllUnites: GetAllUnitesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccueilUiState())
    val uiState: StateFlow<AccueilUiState> = _uiState.asStateFlow()

    init {
        chargerProfil()
    }

    private fun chargerProfil() {
        viewModelScope.launch {
            val utilisateur = getUtilisateurConnecte()
            _uiState.update { it.copy(
                prenom = utilisateur?.nomAffiche?.split(" ")?.firstOrNull() ?: "Élève",
                niveau = utilisateur?.niveau ?: "A1",
                // Données mockées conservées jusqu'à la suite de la Mission B1 :
                progressionGlobale = 0.62f,
                serieJours         = "5",
                unitesTerminees    = "12",
                tempsEtude         = "45m",
                lectureEnCours     = LectureEnCours(
                    titre       = "Ein Tag in Yaoundé",
                    pageActuelle = 1,
                    pageTotale   = 3
                ),
                miniCours = listOf(
                    MiniCours("u-4e-01", "Un jour à Yaoundé",  0.40f),
                    MiniCours("u-6e-01", "Ich stelle mich vor", 0.80f)
                )
            )}

            val eleveId = utilisateur?.id ?: AppConstants.ELEVE_DEMO_ID
            val unitesParId = getAllUnites().first().associateBy { it.id }

            getAssignationsPourEleve(eleveId, utilisateur?.classe).collect { assignations ->
                _uiState.update { it.copy(
                    assignations = assignations.map { a ->
                        AssignationAffichee(
                            uniteId   = a.uniteId,
                            titre     = unitesParId[a.uniteId]?.titre ?: a.uniteId,
                            niveauGer = unitesParId[a.uniteId]?.niveauGer ?: "?"
                        )
                    }
                )}
            }
        }
    }
}
