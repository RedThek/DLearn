package edu.project.dlearn.domain.usecase

import edu.project.dlearn.domain.model.Assignation
import edu.project.dlearn.domain.repository.AssignationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssignerContenuUseCase @Inject constructor(
    private val repository: AssignationRepository
) {
    suspend operator fun invoke(enseignantId: Long, cibleType: String, cibleId: String, uniteId: String) =
        repository.assigner(enseignantId, cibleType, cibleId, uniteId)
}

class GetAssignationsPourEleveUseCase @Inject constructor(
    private val repository: AssignationRepository
) {
    operator fun invoke(eleveId: Long, classe: String?): Flow<List<Assignation>> =
        repository.getAssignationsPourEleve(eleveId, classe)
}
