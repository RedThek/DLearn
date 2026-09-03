package edu.project.dlearn.presentation.accueil

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// TODO: remplacer par lagregation reelle (utilisateur connecte + Room :
// progression de lecture depuis la table oeuvres/chapitres, et suivi des mini-cours
// depuis la meme source que SuiviViewModel).
@HiltViewModel
class AccueilViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        AccueilUiState(
            prenom = "Lena",
            niveau = "A2",
            progressionGlobale = 0.62f,
            serieJours = "5",
            unitesTerminees = "12",
            tempsEtude = "45m",
            lectureEnCours = LectureEnCours(
                titre = "Der Zauberlehrling",
                pageActuelle = 3,
                pageTotale = 5
            ),
            miniCours = listOf(
                MiniCours("contes-courts", "Contes courts", 0.80f),
                MiniCours("expression-ecrite", "Expression écrite", 0.35f)
            )
        )
    )
    val uiState: StateFlow<AccueilUiState> = _uiState.asStateFlow()
}
