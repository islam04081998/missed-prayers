package com.example.missedprayers.data

import com.example.missedprayers.data.dao.DailyPrayerDao
import com.example.missedprayers.data.dao.QadaBacklogDao
import com.example.missedprayers.data.entity.DailyPrayerEntity
import com.example.missedprayers.data.entity.QadaBacklogEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrayerRepository(
    private val dailyPrayerDao: DailyPrayerDao,
    private val qadaBacklogDao: QadaBacklogDao
) {
    companion object {
        val PRAYER_TYPES = listOf("SUBH", "DHUHR", "ASR", "MAGHRIB", "ISHA")

        fun getCurrentDateString(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return sdf.format(Date())
        }
    }

    /**
     * Ensures all 5 Qada backlog rows exist in the database with at least count 0.
     */
    suspend fun initializeBacklogIfNeeded() {
        for (type in PRAYER_TYPES) {
            val existing = qadaBacklogDao.getBacklog(type)
            if (existing == null) {
                qadaBacklogDao.insertOrUpdate(QadaBacklogEntity(prayerType = type, count = 0))
            }
        }
    }

    /**
     * Performs rollover check: finds any past dates (date < todayDate) where rolledOver = 0,
     * calculates missed prayers, increments Qada backlog, and marks rolledOver = true.
     * Also ensures today's daily prayer record exists.
     */
    suspend fun checkAndPerformRollover() {
        initializeBacklogIfNeeded()

        val today = getCurrentDateString()
        val pastUnrolled = dailyPrayerDao.getUncheckedPastPrayers(today)

        for (pastPrayer in pastUnrolled) {
            var subhMissed = 0
            var dhuhrMissed = 0
            var asrMissed = 0
            var maghribMissed = 0
            var ishaMissed = 0

            if (!pastPrayer.subhPrayed) subhMissed++
            if (!pastPrayer.dhuhrPrayed) dhuhrMissed++
            if (!pastPrayer.asrPrayed) asrMissed++
            if (!pastPrayer.maghribPrayed) maghribMissed++
            if (!pastPrayer.ishaPrayed) ishaMissed++

            if (subhMissed > 0) qadaBacklogDao.incrementCount("SUBH", subhMissed)
            if (dhuhrMissed > 0) qadaBacklogDao.incrementCount("DHUHR", dhuhrMissed)
            if (asrMissed > 0) qadaBacklogDao.incrementCount("ASR", asrMissed)
            if (maghribMissed > 0) qadaBacklogDao.incrementCount("MAGHRIB", maghribMissed)
            if (ishaMissed > 0) qadaBacklogDao.incrementCount("ISHA", ishaMissed)

            val rolledOverPrayer = pastPrayer.copy(rolledOver = true)
            dailyPrayerDao.update(rolledOverPrayer)
        }

        // Ensure today's record exists
        val todayRecord = dailyPrayerDao.getPrayerForDate(today)
        if (todayRecord == null) {
            dailyPrayerDao.insertOrUpdate(
                DailyPrayerEntity(
                    date = today,
                    subhPrayed = false,
                    dhuhrPrayed = false,
                    asrPrayed = false,
                    maghribPrayed = false,
                    ishaPrayed = false,
                    rolledOver = false
                )
            )
        }
    }

    fun observeTodayPrayer(): Flow<DailyPrayerEntity?> {
        val today = getCurrentDateString()
        return dailyPrayerDao.observePrayerForDate(today)
    }

    suspend fun getTodayPrayer(): DailyPrayerEntity {
        val today = getCurrentDateString()
        var record = dailyPrayerDao.getPrayerForDate(today)
        if (record == null) {
            record = DailyPrayerEntity(date = today)
            dailyPrayerDao.insertOrUpdate(record)
            record = dailyPrayerDao.getPrayerForDate(today) ?: record
        }
        return record
    }

    suspend fun updateDailyPrayer(prayer: DailyPrayerEntity) {
        dailyPrayerDao.insertOrUpdate(prayer)
    }

    fun observeQadaBacklog(): Flow<List<QadaBacklogEntity>> {
        return qadaBacklogDao.observeAllBacklog()
    }

    suspend fun incrementQada(prayerType: String, delta: Int = 1) {
        qadaBacklogDao.incrementCount(prayerType, delta)
    }

    suspend fun decrementQada(prayerType: String, delta: Int = 1) {
        qadaBacklogDao.decrementCount(prayerType, delta)
    }

    suspend fun setQadaCount(prayerType: String, count: Int) {
        qadaBacklogDao.setCount(prayerType, count)
    }
}
