package edu.project.dlearn.core

/**
 * Constantes partagées entre les couches de l'application.
 * Les valeurs _DEMO sont des placeholders Sprint 2 → à remplacer par la session DataStore (A5).
 */
object AppConstants {
    /**
     * ID de l'élève de démo seedé par [SeedCallback] dans AppModule.
     * Correspond au premier enregistrement INSERT dans la table utilisateur (auto-increment = 1).
     * À remplacer dès que SessionManager est câblé dans les ViewModels (Sprint 3).
     */
    const val ELEVE_DEMO_ID: Long = 1L
}
