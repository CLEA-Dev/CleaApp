package com.drcmind.cleaapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.drcmind.cleaapp.ui.menstrual.MenstrualDashboardScreen

enum class MainDestination(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Cycle("Cycle", Icons.Default.CalendarMonth),
    Leadership("Leadership", Icons.Default.Insights),
    Tempo("Tempo", Icons.Default.QueryStats),
    Marche("Marché", Icons.Default.ShoppingCart)
}

@Composable
fun MainScreen(
    onNavigateToProfile: () -> Unit
) {
    var currentDestination by remember { mutableStateOf(MainDestination.Cycle) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            MainDestination.entries.forEach { destination ->
                item(
                    selected = currentDestination == destination,
                    onClick = { currentDestination = destination },
                    icon = { Icon(destination.icon, contentDescription = destination.label) },
                    label = { Text(destination.label) }
                )
            }
        }
    ) {
        when (currentDestination) {
            MainDestination.Cycle -> MenstrualDashboardScreen(onNavigateToProfile = onNavigateToProfile)
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Écran ${currentDestination.label} en cours de développement")
                }
            }
        }
    }
}
