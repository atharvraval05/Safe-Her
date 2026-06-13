package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.IncidentLog
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentLogDao {
    @Query("SELECT * FROM incident_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<IncidentLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: IncidentLog)

    @Delete
    suspend fun deleteLog(log: IncidentLog)

    @Query("DELETE FROM incident_logs")
    suspend fun clearAllLogs()
}
