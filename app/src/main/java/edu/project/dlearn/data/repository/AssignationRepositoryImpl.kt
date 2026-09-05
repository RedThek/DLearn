package edu.project.dlearn.data.repository

import edu.project.dlearn.data.local.room.AssignationDao
import edu.project.dlearn.data.local.room.AssignationEntity
import edu.project.dlearn.domain.model.Assignation
import edu.project.dlearn.domain.model.CibleAssignation
import edu.project.dlearn.domain.repository.AssignationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class AssignationRepositoryImpl @Inject constructor(
    private val dao: AssignationDao
) : AssignationRepository {

    override suspend fun assigner(
        enseignantId: Long, cibleType: String, cibleId: String, uniteId: String
    ) {
        dao.insert(
            AssignationEntity(
                id              = UUID.randomUUID().toString(),
                enseignantId    = enseignantId,
                cibleType       = cibleType,
                cibleId         = cibleId,
                uniteId         = uniteId
            )
        )
    }

    override fun getAssignationsPourEleve(eleveId: Long, classe: String?): Flow<List<Assignation>> {
        val parEleve = dao.getPourEleve(eleveId.toString())
        val parClasse = if (classe != null) dao.getPourClasse(classe) else flowOf(emptyList())
        return combine(parEleve, parClasse) { direct, viaClasse ->
            (direct + viaClasse)
                .distinctBy { it.id }
                .sortedByDescending { it.dateAssignation }
                .map { it.toDomain() }
        }
    }

    override fun getAssignationsParEnseignant(enseignantId: Long): Flow<List<Assignation>> =
        dao.getParEnseignant(enseignantId).map { list -> list.map { it.toDomain() } }

    private fun AssignationEntity.toDomain() = Assignation(
        id              = id,
        enseignantId    = enseignantId,
        cibleType       = CibleAssignation.valueOf(cibleType),
        cibleId         = cibleId,
        uniteId         = uniteId,
        dateAssignation = dateAssignation
    )
}
