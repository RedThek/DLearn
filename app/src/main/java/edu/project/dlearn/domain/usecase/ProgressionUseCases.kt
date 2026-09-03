package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.ProgressionStats
import edu.project.dlearn.domain.repository.ProgressionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProgressionStatsUseCase @Inject constructor(
    private val repository: ProgressionRepository
) {
    operator fun invoke(eleveId: Long): Flow<ProgressionStats> =
        repository.getProgressionStats(eleveId)
}

class MarquerUniteEnCoursUseCase @Inject constructor(
    private val repository: ProgressionRepository
) {
    suspend operator fun invoke(eleveId: Long, uniteId: String) =
        repository.marquerUniteEnCours(eleveId, uniteId)
}

class MarquerUniteTermineeUseCase @Inject constructor(
    private val repository: ProgressionRepository
) {
    suspend operator fun invoke(eleveId: Long, uniteId: String, scoreMoyen: Float) =
        repository.marquerUniteTerminee(eleveId, uniteId, scoreMoyen)
}
