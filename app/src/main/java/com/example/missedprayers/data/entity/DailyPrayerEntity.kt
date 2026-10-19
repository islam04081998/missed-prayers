package com.example.missedprayers.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_prayers")
data class DailyPrayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val date: String, // "YYYY-MM-DD"
    val subhPrayed: Boolean = false,
    val dhuhrPrayed: Boolean = false,
    val asrPrayed: Boolean = false,
    val maghribPrayed: Boolean = false,
    val ishaPrayed: Boolean = false,
    val rolledOver: Boolean = false
)
