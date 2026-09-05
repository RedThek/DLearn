package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.ProductionEcrite
import edu.project.dlearn.domain.repository.EcritureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Retourne toutes les productions écrites marquées SOUMIS (correctif B-21/B-24).
 * Utilisé par EnseignantViewModel pour peupler l'onglet Corrections du dashboard (FR-27).
 */
class GetProductionsSoumisesUseCase @Inject constructor(
    private val repository: EcritureRepository
) {
    operator fun invoke(): Flow<List<ProductionEcrite>> = repository.getProductionsSoumises()
}
