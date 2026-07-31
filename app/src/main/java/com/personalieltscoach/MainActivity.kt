package com.personalieltscoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.personalieltscoach.ui.CoachViewModel
import com.personalieltscoach.ui.CoachViewModelFactory
import com.personalieltscoach.ui.navigation.CoachApp
import com.personalieltscoach.ui.navigation.Routes
import com.personalieltscoach.ui.theme.CoachTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoachTheme {
                val app = application as CoachApplication
                val viewModel: CoachViewModel = viewModel(factory = CoachViewModelFactory(app.container))
                val startupHasProfile by viewModel.startupHasProfile.collectAsStateWithLifecycle()
                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    viewModel.refreshDate()
                }
                if (startupHasProfile == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    CoachApp(
                        viewModel = viewModel,
                        startDestination = if (startupHasProfile == true) Routes.Home else Routes.Onboarding
                    )
                }
            }
        }
    }
}
