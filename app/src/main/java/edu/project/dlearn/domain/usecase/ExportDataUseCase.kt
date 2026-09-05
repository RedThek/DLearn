package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.repository.SyncRepository
import javax.inject.Inject

/**
 * Exporte les données de l'élève connecté (progression, productions soumises) vers un fichier local
 * JSON, prêt à être partagé via le mécanisme de partage natif Android (ADR-004). Ne réalise PAS le
 * transfert lui-même — voir EXEC-SPRINT3-FRONTEND-AGENT.md Phase 3 pour le déclenchement du partage.
 */
class ExportDataUseCase @Inject constructor(
    private val repository: SyncRepository
) {
    suspend operator fun invoke(eleveId: Long): Result<String> = repository.exporterDonnees(eleveId)
}
