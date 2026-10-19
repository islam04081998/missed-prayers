package com.example.missedprayers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.missedprayers.data.dao.DailyPrayerDao
import com.example.missedprayers.data.dao.QadaBacklogDao
import com.example.missedprayers.data.entity.DailyPrayerEntity
import com.example.missedprayers.data.entity.QadaBacklogEntity

@Database(entities = [DailyPrayerEntity::class, QadaBacklogEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyPrayerDao(): DailyPrayerDao
    abstract fun qadaBacklogDao(): QadaBacklogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "missed_prayers_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
