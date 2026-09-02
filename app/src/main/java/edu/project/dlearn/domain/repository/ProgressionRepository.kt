package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.ProgressionStats
import edu.project.dlearn.domain.model.StatutProgression
import kotlinx.coroutines.flow.Flow

interface ProgressionRepository {
    fun getProgressionStats(eleveId: Long): Flow<ProgressionStats>
    suspend fun marquerUniteEnCours(eleveId: Long, uniteId: String)
    suspend fun marquerUniteTerminee(eleveId: Long, uniteId: String, scoreMoyen: Float)
}
