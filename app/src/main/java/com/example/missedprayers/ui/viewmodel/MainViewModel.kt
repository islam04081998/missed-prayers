package com.example.missedprayers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.missedprayers.data.PrayerRepository
import com.example.missedprayers.data.entity.DailyPrayerEntity
import com.example.missedprayers.data.entity.QadaBacklogEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PrayerRepository) : ViewModel() {

    val todayPrayer: StateFlow<DailyPrayerEntity?> = repository.observeTodayPrayer()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val qadaBacklog: StateFlow<List<QadaBacklogEntity>> = repository.observeQadaBacklog()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.checkAndPerformRollover()
            repository.initializeBacklogIfNeeded()
        }
    }

    fun togglePrayer(prayerType: String) {
        viewModelScope.launch {
            val current = repository.getTodayPrayer()
            val updated = when (prayerType.uppercase()) {
                "SUBH" -> current.copy(subhPrayed = !current.subhPrayed)
                "DHUHR" -> current.copy(dhuhrPrayed = !current.dhuhrPrayed)
                "ASR" -> current.copy(asrPrayed = !current.asrPrayed)
                "MAGHRIB" -> current.copy(maghribPrayed = !current.maghribPrayed)
                "ISHA" -> current.copy(ishaPrayed = !current.ishaPrayed)
                else -> current
            }
            repository.updateDailyPrayer(updated)
        }
    }

    fun incrementQada(prayerType: String, delta: Int = 1) {
        viewModelScope.launch {
            repository.incrementQada(prayerType, delta)
        }
    }

    fun decrementQada(prayerType: String, delta: Int = 1) {
        viewModelScope.launch {
            repository.decrementQada(prayerType, delta)
        }
    }

    fun setQadaCount(prayerType: String, count: Int) {
        viewModelScope.launch {
            repository.setQadaCount(prayerType, count)
        }
    }

    companion object {
        fun provideFactory(repository: PrayerRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(repository) as T
                }
            }
    }
}
