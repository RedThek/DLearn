package edu.project.dlearn.presentation.positionnement

import edu.project.dlearn.domain.model.QuestionPositionnement

sealed interface PositionnementUiState {
    data object Chargement : PositionnementUiState
    data class EnCours(
        val questions: List<QuestionPositionnement>,
        val indexQuestion: Int,
        val indexOptionSelectionnee: Int? = null
    ) : PositionnementUiState {
        val question: QuestionPositionnement get() = questions[indexQuestion]
        val progression: Float get() = (indexQuestion + 1f) / questions.size
    }
    data class Termine(val niveauPropose: String, val score: Int, val total: Int) : PositionnementUiState
}
