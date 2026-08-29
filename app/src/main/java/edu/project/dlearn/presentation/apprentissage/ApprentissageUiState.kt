package edu.project.dlearn.presentation.apprentissage

import edu.project.dlearn.domain.model.ExerciceTexteATrous
import edu.project.dlearn.domain.model.Vocabulaire

sealed interface ApprentissageUiState {
    data object Chargement : ApprentissageUiState
    data class Succes(
        val flashcards: List<Vocabulaire>,
        val exercices: List<ExerciceTexteATrous>,
        val indexFlashcardActuelle: Int = 0,
        val ongletFlashcard: Boolean = true // true = mode flashcard, false = mode texte a trous
    ) : ApprentissageUiState
    data class Erreur(val message: String) : ApprentissageUiState
}
