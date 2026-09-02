package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ApprentissageDao {

    // VocabEntity (flashcards pour répétition espacée) — inchangé
    @Query("""
        SELECT * FROM vocabulaire 
        WHERE niveauCECR = :niveau AND prochainRappel <= :maintenant 
        ORDER BY prochainRappel ASC
    """)
    fun getFlashcardsDues(niveau: String, maintenant: Long = System.currentTimeMillis()): Flow<List<VocabEntity>>

    @Update
    suspend fun updateVocab(vocab: VocabEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabs(vocabs: List<VocabEntity>)

    @Query("SELECT * FROM vocabulaire WHERE id = :id")
    suspend fun getVocabById(id: Long): VocabEntity?

    // ExerciceEntity — table renommée "exercice", filtrer par type TEXTE_A_TROUS
    // pour conserver la compatibilité avec ApprentissageRepositoryImpl
    @Query("SELECT * FROM exercice WHERE uniteId IN (SELECT id FROM unite_apprentissage WHERE niveauGer = :niveau) AND type = 'TEXTE_A_TROUS'")
    fun getExercicesTexteATrous(niveau: String): Flow<List<ExerciceEntity>>

    // Enregistrer la réponse dans reponse_eleve
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReponse(reponse: ReponseEleveEntity)
}
