package edu.project.dlearn.presentation.exercice

import edu.project.dlearn.domain.model.Exercice

sealed interface ExerciceUiState {
    data object Chargement : ExerciceUiState

    /** Aucun exercice trouvé pour cette unité (unité sans exercices seedés). */
    data object Vide : ExerciceUiState

    data class EnCours(
        val exercices: List<Exercice>,
        val indexActuel: Int,
        val reponseSelectionnee: String? = null,
        /** null = pas encore validé, true/false = résultat affiché après validation. */
        val resultat: Boolean? = null
    ) : ExerciceUiState {
        val exerciceActuel: Exercice get() = exercices[indexActuel]
        val progression: Float get() = (indexActuel + 1f) / exercices.size
    }

    data class Termine(val bonnesReponses: Int, val total: Int) : ExerciceUiState
}
