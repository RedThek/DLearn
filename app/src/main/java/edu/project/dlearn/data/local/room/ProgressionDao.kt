package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressionDao {

    @Query("SELECT * FROM progression WHERE eleveId = :eleveId ORDER BY dateMiseAJour DESC")
    fun getProgressionByEleve(eleveId: Long): Flow<List<ProgressionEntity>>

    @Query("SELECT * FROM progression WHERE eleveId = :eleveId AND uniteId = :uniteId LIMIT 1")
    suspend fun getProgressionForUnite(eleveId: Long, uniteId: String): ProgressionEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProgression(progression: ProgressionEntity)

    @Update
    suspend fun updateProgression(progression: ProgressionEntity)

    // Upsert : créer si absent, mettre à jour sinon
    @Query("""
        INSERT OR REPLACE INTO progression (id, eleveId, uniteId, statut, scoreMoyen, dateMiseAJour)
        VALUES (:id, :eleveId, :uniteId, :statut, :scoreMoyen, :dateMiseAJour)
    """)
    suspend fun upsertProgression(
        id: String,
        eleveId: Long,
        uniteId: String,
        statut: String,
        scoreMoyen: Float?,
        dateMiseAJour: Long
    )

    @Query("SELECT COUNT(*) FROM progression WHERE eleveId = :eleveId AND statut = 'TERMINE'")
    fun countUnitesTerminees(eleveId: Long): Flow<Int>

    @Query("""
        SELECT AVG(scoreMoyen) FROM progression 
        WHERE eleveId = :eleveId AND scoreMoyen IS NOT NULL
    """)
    fun getScoreMoyenGlobal(eleveId: Long): Flow<Float?>
}
