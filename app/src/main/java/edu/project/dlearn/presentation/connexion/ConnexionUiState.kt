package edu.project.dlearn.presentation.connexion

import edu.project.dlearn.domain.model.Role

data class ConnexionUiState(
    val roleSelectionne: Role = Role.ELEVE,
    val identifiant: String = "",
    val motDePasse: String = "",
    val motDePasseVisible: Boolean = false,
    val enChargement: Boolean = false,
    val messageErreur: String? = null
)
