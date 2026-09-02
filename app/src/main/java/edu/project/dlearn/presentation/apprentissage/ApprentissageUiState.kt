package edu.project.dlearn.presentation.apprentissage

import edu.project.dlearn.domain.model.ExtraitAvecGlossaire
import edu.project.dlearn.domain.model.UniteApprentissage

sealed interface ApprentissageUiState {
    data object Chargement : ApprentissageUiState

    // Vue liste des unités disponibles
    data class Bibliotheque(
        val unites: List<UniteApprentissage>
    ) : ApprentissageUiState

    // Vue lecture d'une unité ouverte
    data class LectureUnite(
        val unite: UniteApprentissage,
        val extrait: ExtraitAvecGlossaire?,
        val motSelectionne: String? = null   // pour le pop-up de glossaire
    ) : ApprentissageUiState

    data class Erreur(val message: String) : ApprentissageUiState
}
