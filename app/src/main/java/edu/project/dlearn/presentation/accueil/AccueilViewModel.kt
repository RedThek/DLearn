package edu.project.dlearn.presentation.accueil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.project.dlearn.domain.usecase.GetUtilisateurConnecteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Mission B1 (Sprint 4) : remplacer les données mockées par les vraies données Room
// (progression, unité en cours, stats) via GetProgressionStatsUseCase + ContenuRepository.
@HiltViewModel
class AccueilViewModel @Inject constructor(
    private val getUtilisateurConnecte: GetUtilisateurConnecteUseCase
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
                // Données mockées conservées jusqu'à Mission B1 :
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
        }
    }
}
