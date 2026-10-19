package com.example.missedprayers.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qada_backlog")
data class QadaBacklogEntity(
    @PrimaryKey
    val prayerType: String, // "SUBH", "DHUHR", "ASR", "MAGHRIB", "ISHA"
    val count: Int = 0
)
