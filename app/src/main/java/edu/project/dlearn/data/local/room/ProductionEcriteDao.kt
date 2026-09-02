package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductionEcriteDao {

    @Query("SELECT * FROM production_ecrite WHERE eleveId = :eleveId ORDER BY dateModification DESC")
    fun getProductionsByEleve(eleveId: Long): Flow<List<ProductionEcriteEntity>>

    @Query("SELECT * FROM production_ecrite WHERE eleveId = :eleveId AND uniteId = :uniteId LIMIT 1")
    suspend fun getProductionForUnite(eleveId: Long, uniteId: String): ProductionEcriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(production: ProductionEcriteEntity)

    @Update
    suspend fun update(production: ProductionEcriteEntity)

    @Query("DELETE FROM production_ecrite WHERE id = :id")
    suspend fun delete(id: String)
}
