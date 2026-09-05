package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assignation: AssignationEntity)

    @Query("SELECT * FROM assignation WHERE cibleType = 'ELEVE' AND cibleId = :eleveId ORDER BY dateAssignation DESC")
    fun getPourEleve(eleveId: String): Flow<List<AssignationEntity>>

    @Query("SELECT * FROM assignation WHERE cibleType = 'CLASSE' AND cibleId = :classe ORDER BY dateAssignation DESC")
    fun getPourClasse(classe: String): Flow<List<AssignationEntity>>

    @Query("SELECT * FROM assignation WHERE enseignantId = :enseignantId ORDER BY dateAssignation DESC")
    fun getParEnseignant(enseignantId: Long): Flow<List<AssignationEntity>>
}
