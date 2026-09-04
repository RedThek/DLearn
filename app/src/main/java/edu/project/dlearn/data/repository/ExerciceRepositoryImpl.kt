package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.ApprentissageDao
import edu.project.dlearn.data.local.room.ContenuDao
import edu.project.dlearn.data.local.room.ExerciceEntity
import edu.project.dlearn.data.local.room.OptionExerciceEntity
import edu.project.dlearn.data.local.room.ReponseEleveEntity
import edu.project.dlearn.domain.model.Exercice
import edu.project.dlearn.domain.model.OptionExercice
import edu.project.dlearn.domain.model.TypeExercice
import edu.project.dlearn.domain.repository.ExerciceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

/**
 * Note d'architecture : injecte à la fois ContenuDao (lecture des exercices/options,
 * déjà utilisé par ContenuRepositoryImpl) et ApprentissageDao (écriture dans
 * reponse_eleve, déjà utilisé pour les flashcards). Choix pragmatique à diff minimal
 * pour ce sprint plutôt que de déplacer insertReponse() vers ContenuDao — à réévaluer
 * lors d'un futur nettoyage architectural si les deux DAO continuent de diverger.
 */
class ExerciceRepositoryImpl @Inject constructor(
    private val contenuDao: ContenuDao,
    private val apprentissageDao: ApprentissageDao
) : ExerciceRepository {

    override fun getExercicesByUnite(uniteId: String): Flow<List<Exercice>> =
        contenuDao.getExercicesByUnite(uniteId).map { entites ->
            entites.map { entite -> entite.toDomain(chargerOptionsSiQcm(entite)) }
        }

    private suspend fun chargerOptionsSiQcm(entite: ExerciceEntity): List<OptionExercice> =
        if (entite.type == TypeExercice.QCM.name) {
            contenuDao.getOptionsByExercice(entite.id).map { it.toDomain() }
        } else {
            emptyList()
        }

    override suspend fun enregistrerReponse(
        eleveId: Long,
        exerciceId: String,
        reponseDonnee: String,
        estCorrecte: Boolean
    ) {
        apprentissageDao.insertReponse(
            ReponseEleveEntity(
                id = UUID.randomUUID().toString(),
                eleveId = eleveId,
                exerciceId = exerciceId,
                reponseDonnee = reponseDonnee,
                estCorrecte = estCorrecte
            )
        )
    }

    private fun ExerciceEntity.toDomain(options: List<OptionExercice>) = Exercice(
        id = id,
        uniteId = uniteId,
        type = TypeExercice.valueOf(type),
        enonce = enonce,
        correctionAttendue = correctionAttendue,
        options = options
    )

    private fun OptionExerciceEntity.toDomain() = OptionExercice(
        id = id,
        texte = texteOption,
        estCorrecte = estCorrecte
    )
}
