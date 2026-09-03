package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContenuDao {

    // --- UniteApprentissage ---
    @Query("SELECT * FROM unite_apprentissage ORDER BY ordreAffichage ASC")
    fun getAllUnites(): Flow<List<UniteApprentissageEntity>>

    @Query("SELECT * FROM unite_apprentissage WHERE niveauGer = :niveau ORDER BY ordreAffichage ASC")
    fun getUnitesByNiveau(niveau: String): Flow<List<UniteApprentissageEntity>>

    @Query("SELECT * FROM unite_apprentissage WHERE id = :id")
    suspend fun getUniteById(id: String): UniteApprentissageEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUnites(unites: List<UniteApprentissageEntity>)

    // --- ExtraitLitteraire ---
    @Query("SELECT * FROM extrait_litteraire WHERE uniteId = :uniteId")
    fun getExtraitsByUnite(uniteId: String): Flow<List<ExtraitLitteraireEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExtraits(extraits: List<ExtraitLitteraireEntity>)

    // --- GlossaireEntree ---
    @Query("SELECT * FROM glossaire_entree WHERE extraitId = :extraitId")
    fun getGlossaireByExtrait(extraitId: String): Flow<List<GlossaireEntreeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGlossaire(entrees: List<GlossaireEntreeEntity>)

    // --- Exercice ---
    @Query("SELECT * FROM exercice WHERE uniteId = :uniteId")
    fun getExercicesByUnite(uniteId: String): Flow<List<ExerciceEntity>>

    @Query("SELECT * FROM exercice WHERE uniteId = :uniteId AND type = :type")
    fun getExercicesByUniteAndType(uniteId: String, type: String): Flow<List<ExerciceEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercices(exercices: List<ExerciceEntity>)

    // --- OptionExercice ---
    @Query("SELECT * FROM option_exercice WHERE exerciceId = :exerciceId")
    suspend fun getOptionsByExercice(exerciceId: String): List<OptionExerciceEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOptions(options: List<OptionExerciceEntity>)

    // --- Utilitaire seed ---
    @Query("SELECT COUNT(*) FROM unite_apprentissage")
    suspend fun countUnites(): Int
}
