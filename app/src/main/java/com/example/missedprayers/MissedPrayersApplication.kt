package com.example.missedprayers

import android.app.Application
import com.example.missedprayers.data.AppDatabase
import com.example.missedprayers.data.PrayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MissedPrayersApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { PrayerRepository(database.dailyPrayerDao(), database.qadaBacklogDao()) }

    override fun onCreate() {
        super.onCreate()
        // Run rollover check on app startup in background scope
        CoroutineScope(Dispatchers.IO).launch {
            repository.checkAndPerformRollover()
        }
    }
}
