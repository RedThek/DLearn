package edu.project.dlearn.presentation.connexion

import edu.project.dlearn.domain.model.Utilisateur

data class SelectionProfilUiState(
    val profils: List<Utilisateur> = emptyList(),
    val enChargement: Boolean = false
)
