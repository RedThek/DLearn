package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.ProductionEcrite
import kotlinx.coroutines.flow.Flow

interface EcritureRepository {
    fun getProductionsByEleve(eleveId: Long): Flow<List<ProductionEcrite>>
    suspend fun getOrCreateBrouillon(eleveId: Long, uniteId: String): ProductionEcrite
    suspend fun sauvegarderBrouillon(production: ProductionEcrite)
    suspend fun soumettre(productionId: String, autoEvaluationJson: String?)
}
