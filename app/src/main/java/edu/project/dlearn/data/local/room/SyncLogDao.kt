package edu.project.dlearn.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: SyncLogEntity)

    @Query("SELECT * FROM sync_log ORDER BY dateSync DESC LIMIT 1")
    fun getDernierLog(): Flow<SyncLogEntity?>

    @Query("SELECT * FROM sync_log ORDER BY dateSync DESC")
    fun getTousLesLogs(): Flow<List<SyncLogEntity>>
}
