package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ApprentissageDao {

    @Query("SELECT * FROM vocabulaire WHERE niveauCECR = :niveau AND prochainRappel <= :maintenant ORDER BY prochainRappel ASC")
    fun getFlashcardsDues(niveau: String, maintenant: Long = System.currentTimeMillis()): Flow<List<VocabEntity>>

    @Query("SELECT * FROM exercice_texte_a_trous WHERE niveauCECR = :niveau")
    fun getExercices(niveau: String): Flow<List<ExerciceEntity>>

    @Update
    suspend fun updateVocab(vocab: VocabEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocab(vocab: List<VocabEntity>)

    @Query("SELECT * FROM vocabulaire WHERE id = :id")
    suspend fun getVocabById(id: Long): VocabEntity?
}
