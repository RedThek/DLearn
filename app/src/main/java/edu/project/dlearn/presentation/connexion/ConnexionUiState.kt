package edu.project.dlearn.presentation.connexion

import edu.project.dlearn.domain.model.Role
import edu.project.dlearn.presentation.selectionprofil.ProfilResume

data class ConnexionUiState(
    val roleSelectionne: Role = Role.ELEVE,
    val identifiant: String = "",
    val motDePasse: String = "",
    val motDePasseVisible: Boolean = false,
    val enChargement: Boolean = false,
    val messageErreur: String? = null,
    /** Profils déjà enregistrés sur cet appareil — affichés si non vide. */
    val profilsExistants: List<ProfilResume> = emptyList()
)
