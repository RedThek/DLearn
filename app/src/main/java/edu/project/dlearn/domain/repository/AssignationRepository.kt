package edu.project.dlearn.domain.repository

import edu.project.dlearn.domain.model.Assignation
import kotlinx.coroutines.flow.Flow

interface AssignationRepository {
    suspend fun assigner(enseignantId: Long, cibleType: String, cibleId: String, uniteId: String)

    /** Combine assignations directes (ELEVE) et par classe (CLASSE) pour un élève donné. */
    fun getAssignationsPourEleve(eleveId: Long, classe: String?): Flow<List<Assignation>>

    fun getAssignationsParEnseignant(enseignantId: Long): Flow<List<Assignation>>
}
