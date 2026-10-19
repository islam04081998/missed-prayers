package com.example.missedprayers.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Today
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.missedprayers.R
import com.example.missedprayers.ui.navigation.AppScreen
import com.example.missedprayers.ui.navigation.rememberNavigationState
import com.example.missedprayers.ui.navigation.Navigator
import com.example.missedprayers.ui.navigation.toEntries
import com.example.missedprayers.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val todayPrayer by viewModel.todayPrayer.collectAsStateWithLifecycle()
    val qadaBacklog by viewModel.qadaBacklog.collectAsStateWithLifecycle()

    val navigationState = rememberNavigationState(
        startRoute = AppScreen.Today,
        topLevelRoutes = setOf(AppScreen.Today, AppScreen.QadaBacklog)
    )
    val navigator = remember { Navigator(navigationState) }

    val entryProvider = entryProvider<NavKey> {
        entry<AppScreen.Today> {
            TodayScreen(
                todayPrayer = todayPrayer,
                onTogglePrayer = { prayerType -> viewModel.togglePrayer(prayerType) }
            )
        }
        entry<AppScreen.QadaBacklog> {
            QadaBacklogScreen(
                qadaList = qadaBacklog,
                onIncrement = { type -> viewModel.incrementQada(type) },
                onDecrement = { type -> viewModel.decrementQada(type) },
                onSetCount = { type, count -> viewModel.setQadaCount(type, count) }
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navigationState.topLevelRoute == AppScreen.Today,
                    onClick = { navigator.navigate(AppScreen.Today) },
                    icon = { Icon(Icons.Rounded.Today, contentDescription = stringResource(R.string.nav_today)) },
                    label = { Text(stringResource(R.string.nav_today)) }
                )
                NavigationBarItem(
                    selected = navigationState.topLevelRoute == AppScreen.QadaBacklog,
                    onClick = { navigator.navigate(AppScreen.QadaBacklog) },
                    icon = { Icon(Icons.Rounded.List, contentDescription = stringResource(R.string.nav_qada_backlog)) },
                    label = { Text(stringResource(R.string.nav_qada_backlog)) }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            entries = navigationState.toEntries(entryProvider),
            onBack = { navigator.goBack() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
