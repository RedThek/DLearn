package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.ProductionEcriteDao
import edu.project.dlearn.data.local.room.ProductionEcriteEntity
import edu.project.dlearn.domain.model.ProductionEcrite
import edu.project.dlearn.domain.repository.EcritureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class EcritureRepositoryImpl @Inject constructor(
    private val dao: ProductionEcriteDao
) : EcritureRepository {

    override fun getProductionsByEleve(eleveId: Long): Flow<List<ProductionEcrite>> =
        dao.getProductionsByEleve(eleveId).map { list -> list.map { it.toDomain() } }

    override suspend fun getOrCreateBrouillon(eleveId: Long, uniteId: String): ProductionEcrite {
        val existant = dao.getProductionForUnite(eleveId, uniteId)
        if (existant != null) return existant.toDomain()

        val nouveau = ProductionEcriteEntity(
            id            = UUID.randomUUID().toString(),
            eleveId       = eleveId,
            uniteId       = uniteId,
            contenuTexte  = ""
        )
        dao.insertOrReplace(nouveau)
        return nouveau.toDomain()
    }

    override suspend fun sauvegarderBrouillon(production: ProductionEcrite) {
        dao.insertOrReplace(production.toEntity())
    }

    override suspend fun soumettre(productionId: String, autoEvaluationJson: String?) {
        // TODO Sprint 3 : marquer la production comme soumise (champ statut à ajouter)
        // Pour l'instant, la sauvegarde de l'auto-évaluation fait office de "soumission locale".
    }

    private fun ProductionEcriteEntity.toDomain() = ProductionEcrite(
        id              = id,
        eleveId         = eleveId,
        uniteId         = uniteId,
        contenuTexte    = contenuTexte,
        dateModification= dateModification,
        autoEvaluationJson = autoEvaluationJson
    )

    private fun ProductionEcrite.toEntity() = ProductionEcriteEntity(
        id                  = id,
        eleveId             = eleveId,
        uniteId             = uniteId,
        contenuTexte        = contenuTexte,
        dateModification    = System.currentTimeMillis(),
        autoEvaluationJson  = autoEvaluationJson
    )
}
