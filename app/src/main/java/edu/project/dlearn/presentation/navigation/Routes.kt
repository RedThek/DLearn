package edu.project.dlearn.presentation.navigation

/**
 * Identifiants de routes pour le graphe de navigation racine.
 * Fichier extrait de NavGraph.kt pour permettre la référence
 * depuis SelectionProfilViewModel (ADR-014, Mission A3).
 */
internal object Route {
    const val CONNEXION       = "connexion"
    const val SELECTION_PROFIL = "selection_profil"
    const val POSITIONNEMENT  = "positionnement"
    const val MAIN            = "main"
    const val CREATION_ELEVE   = "creation_eleve"
    const val RESULTAT_CREATION_ELEVE = "resultat_creation_eleve"
}
