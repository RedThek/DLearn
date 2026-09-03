package edu.project.dlearn.presentation.profil

import androidx.compose.ui.graphics.vector.ImageVector

data class Badge(
    val id: String,
    val icone: ImageVector,
    val deverrouille: Boolean
)

data class ProfilUiState(
    val nomComplet: String = "",
    val classe: String = "",
    val niveauActuel: String = "A1",
    val niveauCible: String = "A2",
    val progressionVersCible: Float = 0f, // 0f..1f
    val langueInterface: String = "Français",
    val modeHorsLigneActif: Boolean = true,
    val notificationsActives: Boolean = true,
    val derniereSynchro: String = "Il y a 2h",
    val synchronisationEnCours: Boolean = false,
    val badges: List<Badge> = emptyList()
)
