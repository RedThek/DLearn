package edu.project.dlearn.core

/**
 * Constantes partagées entre les couches de l'application.
 * Les valeurs _DEMO sont des placeholders Sprint 2 → à remplacer par la session DataStore (A5).
 */
object AppConstants {
    /**
     * ID de l'élève de démo seedé par [SeedCallback] dans AppModule.
     * Correspond au premier enregistrement INSERT dans la table utilisateur (auto-increment = 1).
     * À remplacer dès que SessionManager est câblé dans les ViewModels (Sprint 3, partiel).
     */
    const val ELEVE_DEMO_ID: Long = 1L

    /**
     * Niveaux scolaires du MVP (ADR-008, portée confirmée le 2026-08-28 : collège
     * complet 6e-3e, lycée reporté). Une seule classe par niveau — aucune subdivision
     * A/B, cohérent avec 09-cartographie-contenu-pedagogique.md section 2.
     * Utilisé par CreationEleveScreen (Frontend, B-18) comme unique source de vérité
     * pour éviter toute divergence future entre écrans.
     */
    val NIVEAUX_COLLEGE: List<String> = listOf("6ème", "5ème", "4ème", "3ème")
}
