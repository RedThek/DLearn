package edu.project.dlearn.domain.repository

interface SyncRepository {
    /** Génère un fichier d'export local et retourne son chemin absolu en cas de succès. */
    suspend fun exporterDonnees(eleveId: Long): Result<String>
}
