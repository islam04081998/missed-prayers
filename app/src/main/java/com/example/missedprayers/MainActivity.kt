package com.example.missedprayers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.missedprayers.ui.screens.MainScreen
import com.example.missedprayers.ui.theme.MissedPrayersTheme
import com.example.missedprayers.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory((application as MissedPrayersApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MissedPrayersTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
