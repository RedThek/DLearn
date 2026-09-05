package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.ApprentissageDao
import edu.project.dlearn.data.local.room.ProgressionDao
import edu.project.dlearn.domain.model.ProgressionStats
import edu.project.dlearn.domain.repository.ProgressionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class ProgressionRepositoryImpl @Inject constructor(
    private val dao: ProgressionDao,
    private val apprentissageDao: ApprentissageDao
) : ProgressionRepository {

    override fun getProgressionStats(eleveId: Long): Flow<ProgressionStats> = flow {
        dao.countUnitesTerminees(eleveId).combine(dao.getScoreMoyenGlobal(eleveId)) { u, s -> u to s }
            .collect { (unitesTerminees, scoreMoyen) ->
                val streak = calculerStreak(apprentissageDao.getDatesActivite(eleveId))
                emit(
                    ProgressionStats(
                        motsAppris   = unitesTerminees * 8,
                        streakJours  = streak,
                        tauxReussite = ((scoreMoyen ?: 0f) * 100).toInt(),
                        unitesTerminees = unitesTerminees,
                        competencesParNiveau = mapOf(
                            "A1" to if (unitesTerminees >= 2) 0.8f else unitesTerminees * 0.4f,
                            "A2" to if (unitesTerminees >= 4) 0.6f else (unitesTerminees - 2).coerceAtLeast(0) * 0.3f
                        )
                    )
                )
            }
    }

    /**
     * Streak = nombre de jours calendaires consécutifs se terminant aujourd'hui (ou hier, tolérance) avec
     * au moins une activité (reponse_eleve). Calcul en Kotlin plutôt qu'en SQL (SQLite a un support limité
     * des fonctions de date) — acceptable au volume attendu pour un élève (quelques centaines de réponses).
     */
    private fun calculerStreak(datesActiviteMs: List<Long>): Int {
        if (datesActiviteMs.isEmpty()) return 0

        fun jourDe(ms: Long): Long {
            val cal = Calendar.getInstance().apply {
                timeInMillis = ms
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }
            return cal.timeInMillis
        }

        val joursDistincts = datesActiviteMs.map { jourDe(it) }.distinct().sortedDescending()
        val unJourMs = 24L * 60 * 60 * 1000
        val aujourdHui = jourDe(System.currentTimeMillis())

        // Tolère que la dernière activité soit hier (l'élève n'a pas encore agi aujourd'hui)
        var curseur = if (joursDistincts.first() == aujourdHui || joursDistincts.first() == aujourdHui - unJourMs) {
            joursDistincts.first()
        } else return 0

        var streak = 0
        for (jour in joursDistincts) {
            if (jour == curseur) {
                streak++
                curseur -= unJourMs
            } else break
        }
        return streak
    }

    override suspend fun marquerUniteEnCours(eleveId: Long, uniteId: String) {
        dao.upsertProgression(
            id = UUID.randomUUID().toString(), eleveId = eleveId, uniteId = uniteId,
            statut = "EN_COURS", scoreMoyen = null, dateMiseAJour = System.currentTimeMillis()
        )
    }

    override suspend fun marquerUniteTerminee(eleveId: Long, uniteId: String, scoreMoyen: Float) {
        dao.upsertProgression(
            id = UUID.randomUUID().toString(), eleveId = eleveId, uniteId = uniteId,
            statut = "TERMINE", scoreMoyen = scoreMoyen, dateMiseAJour = System.currentTimeMillis()
        )
    }
}
