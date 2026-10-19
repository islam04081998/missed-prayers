package com.example.missedprayers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.missedprayers.data.entity.QadaBacklogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QadaBacklogDao {
    @Query("SELECT * FROM qada_backlog")
    fun observeAllBacklog(): Flow<List<QadaBacklogEntity>>

    @Query("SELECT * FROM qada_backlog WHERE prayerType = :prayerType")
    suspend fun getBacklog(prayerType: String): QadaBacklogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(backlog: QadaBacklogEntity)

    @Query("UPDATE qada_backlog SET count = count + :delta WHERE prayerType = :prayerType")
    suspend fun incrementCount(prayerType: String, delta: Int)

    @Query("UPDATE qada_backlog SET count = MAX(0, count - :delta) WHERE prayerType = :prayerType")
    suspend fun decrementCount(prayerType: String, delta: Int)

    @Query("UPDATE qada_backlog SET count = :count WHERE prayerType = :prayerType")
    suspend fun setCount(prayerType: String, count: Int)
}
