package com.example.missedprayers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.missedprayers.data.entity.DailyPrayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyPrayerDao {
    @Query("SELECT * FROM daily_prayers WHERE date = :date LIMIT 1")
    suspend fun getPrayerForDate(date: String): DailyPrayerEntity?

    @Query("SELECT * FROM daily_prayers WHERE date = :date LIMIT 1")
    fun observePrayerForDate(date: String): Flow<DailyPrayerEntity?>

    @Query("SELECT * FROM daily_prayers WHERE date < :todayDate AND rolledOver = 0")
    suspend fun getUncheckedPastPrayers(todayDate: String): List<DailyPrayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(prayer: DailyPrayerEntity): Long

    @Update
    suspend fun update(prayer: DailyPrayerEntity)
}
