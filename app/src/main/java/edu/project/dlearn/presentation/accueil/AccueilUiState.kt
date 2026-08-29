package edu.project.dlearn.presentation.accueil

data class LectureEnCours(
    val titre: String,
    val pageActuelle: Int,
    val pageTotale: Int
)

data class MiniCours(
    val id: String,
    val nom: String,
    val progression: Float // 0f..1f
)

data class AccueilUiState(
    val prenom: String = "",
    val niveau: String = "A1",
    val progressionGlobale: Float = 0f, // 0f..1f
    val lectureEnCours: LectureEnCours? = null,
    val miniCours: List<MiniCours> = emptyList()
    //val isLoading: Boolean = false,
    //val appName: String = "Liteschreib Ikii"
)