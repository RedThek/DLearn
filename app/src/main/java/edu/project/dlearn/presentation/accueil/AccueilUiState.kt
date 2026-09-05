package edu.project.dlearn.presentation.accueil

data class LectureEnCours(
    val titre: String,
    val pageActuelle: Int,
    val pageTotale: Int
)

data class MiniCours(
    val id: String,
    val nom: String,
    val progression: Float
)

data class AssignationAffichee(
    val uniteId: String,
    val titre: String,
    val niveauGer: String
)

data class AccueilUiState(
    val prenom: String = "",
    val niveau: String = "A1",
    val progressionGlobale: Float = 0f,
    val serieJours: String = "0",
    val unitesTerminees: String = "0",
    val tempsEtude: String = "0m",
    val lectureEnCours: LectureEnCours? = null,
    val miniCours: List<MiniCours> = emptyList(),
    val assignations: List<AssignationAffichee> = emptyList()
)
