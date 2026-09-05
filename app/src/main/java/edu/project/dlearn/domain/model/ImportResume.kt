package edu.project.dlearn.domain.model

/**
 * Résumé d'une opération d'import (ADR-018). Affiché à l'enseignant après import d'un fichier
 * d'export élève (v1, ADR-004) pour confirmer ce qui a réellement été appliqué.
 */
data class ImportResume(
    val eleveId: Long,
    val progressionsMisesAJour: Int,
    val progressionsIgnorees: Int,
    val productionsMisesAJour: Int,
    val productionsIgnorees: Int
)
