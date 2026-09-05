package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.ImportResume

interface SyncRepository {
    /** Génère un fichier d'export local et retourne son chemin absolu en cas de succès. */
    suspend fun exporterDonnees(eleveId: Long): Result<String>

    /**
     * Importe un fichier d'export élève (format v1, ADR-004) depuis une URI (typiquement issue d'un
     * sélecteur de fichier système, `ACTION_OPEN_DOCUMENT`). Fusionne par timestamp (ADR-018) :
     * un enregistrement importé ne remplace l'existant que s'il est au moins aussi récent.
     */
    suspend fun importerDonnees(uriString: String): Result<ImportResume>
}
