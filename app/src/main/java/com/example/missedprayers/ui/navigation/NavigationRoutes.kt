package com.example.missedprayers.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppScreen : NavKey {
    @Serializable
    data object Today : AppScreen

    @Serializable
    data object QadaBacklog : AppScreen
}
