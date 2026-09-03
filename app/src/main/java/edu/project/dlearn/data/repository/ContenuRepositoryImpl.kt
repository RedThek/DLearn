package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.ContenuDao
import edu.project.dlearn.data.local.room.ExtraitLitteraireEntity
import edu.project.dlearn.data.local.room.UniteApprentissageEntity
import edu.project.dlearn.domain.model.EntreeGlossaire
import edu.project.dlearn.domain.model.ExtraitAvecGlossaire
import edu.project.dlearn.domain.model.UniteApprentissage
import edu.project.dlearn.domain.repository.ContenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContenuRepositoryImpl @Inject constructor(
    private val dao: ContenuDao
) : ContenuRepository {

    override fun getAllUnites(): Flow<List<UniteApprentissage>> =
        dao.getAllUnites().map { list -> list.map { it.toDomain() } }

    override fun getUnitesByNiveau(niveauGer: String): Flow<List<UniteApprentissage>> =
        dao.getUnitesByNiveau(niveauGer).map { list -> list.map { it.toDomain() } }

    override suspend fun getExtraitAvecGlossaire(uniteId: String): ExtraitAvecGlossaire? {
        val extrait = dao.getExtraitsByUnite(uniteId).first().firstOrNull() ?: return null
        val glossaire = dao.getGlossaireByExtrait(extrait.id).first()
        return ExtraitAvecGlossaire(
            id            = extrait.id,
            uniteId       = extrait.uniteId,
            texteAllemand = extrait.texteAllemand,
            auteur        = extrait.auteur,
            glossaire     = glossaire.map { EntreeGlossaire(it.motAllemand, it.traductionFr) }
        )
    }

    private fun UniteApprentissageEntity.toDomain() = UniteApprentissage(
        id                    = id,
        niveauGer             = niveauGer,
        titre                 = titre,
        objectifsApprentissage= objectifsApprentissage,
        ordreAffichage        = ordreAffichage,
        isValidated           = isValidated
    )
}
