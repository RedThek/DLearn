package edu.project.dlearn.presentation.selectionprofil

import edu.project.dlearn.domain.model.Role

/**
 * Résumé d'un profil local affiché dans le sélecteur (couche présentation).
 * Ne contient jamais le hash du mot de passe.
 */
data class ProfilResume(
    val id: Long,
    val nomAffiche: String,
    val role: Role,
    val classe: String?          // non null pour un Élève, null pour un Enseignant
)

sealed interface SelectionProfilUiState {
    data object Chargement : SelectionProfilUiState
    data class AvecProfils(val profils: List<ProfilResume>) : SelectionProfilUiState
    /** Aucun compte local — ne devrait pas se produire si la navigation est correcte. */
    data object SansProfil : SelectionProfilUiState
}
