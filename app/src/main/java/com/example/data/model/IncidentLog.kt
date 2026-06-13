package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incident_logs")
data class IncidentLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val location: String, // "Latitude, Longitude" or "Address"
    val severity: String // "LOW", "MEDIUM", "HIGH"
)
