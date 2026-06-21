package com.drcmind.cleaapp.ui.auth.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.drcmind.cleaapp.ui.navigation.AppDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigate: (AppDestination) -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    val destination by viewModel.navigationEvent.collectAsState(initial = null)

    LaunchedEffect(destination) {
        destination?.let { onNavigate(it) }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("CLEA", style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.primary)
    }
}
