package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.ProductionEcrite
import edu.project.dlearn.domain.repository.EcritureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrCreateBrouillonUseCase @Inject constructor(
    private val repository: EcritureRepository
) {
    suspend operator fun invoke(eleveId: Long, uniteId: String): ProductionEcrite =
        repository.getOrCreateBrouillon(eleveId, uniteId)
}

class SauvegarderBrouillonUseCase @Inject constructor(
    private val repository: EcritureRepository
) {
    suspend operator fun invoke(production: ProductionEcrite) =
        repository.sauvegarderBrouillon(production)
}

class SoumettreProductionUseCase @Inject constructor(
    private val repository: EcritureRepository
) {
    suspend operator fun invoke(productionId: String, contenuTexte: String, autoEvaluationJson: String?) =
        repository.soumettre(productionId, contenuTexte, autoEvaluationJson)
}
