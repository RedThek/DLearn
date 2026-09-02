package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.ProgressionDao
import edu.project.dlearn.domain.model.ProgressionStats
import edu.project.dlearn.domain.repository.ProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID
import javax.inject.Inject

class ProgressionRepositoryImpl @Inject constructor(
    private val dao: ProgressionDao
) : ProgressionRepository {

    override fun getProgressionStats(eleveId: Long): Flow<ProgressionStats> =
        combine(
            dao.countUnitesTerminees(eleveId),
            dao.getScoreMoyenGlobal(eleveId)
        ) { unitesTerminees, scoreMoyen ->
            ProgressionStats(
                motsAppris   = unitesTerminees * 8, // estimation : ~8 mots par unité validée
                streakJours  = 0,                   // TODO Sprint 3 : calculer depuis ReponseEleve
                tauxReussite = ((scoreMoyen ?: 0f) * 100).toInt(),
                competencesParNiveau = mapOf(
                    "A1" to if (unitesTerminees >= 2) 0.8f else unitesTerminees * 0.4f,
                    "A2" to if (unitesTerminees >= 4) 0.6f else (unitesTerminees - 2).coerceAtLeast(0) * 0.3f
                )
            )
        }

    override suspend fun marquerUniteEnCours(eleveId: Long, uniteId: String) {
        dao.upsertProgression(
            id            = UUID.randomUUID().toString(),
            eleveId       = eleveId,
            uniteId       = uniteId,
            statut        = "EN_COURS",
            scoreMoyen    = null,
            dateMiseAJour = System.currentTimeMillis()
        )
    }

    override suspend fun marquerUniteTerminee(eleveId: Long, uniteId: String, scoreMoyen: Float) {
        dao.upsertProgression(
            id            = UUID.randomUUID().toString(),
            eleveId       = eleveId,
            uniteId       = uniteId,
            statut        = "TERMINE",
            scoreMoyen    = scoreMoyen,
            dateMiseAJour = System.currentTimeMillis()
        )
    }
}
