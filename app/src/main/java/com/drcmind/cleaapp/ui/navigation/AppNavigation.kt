package com.drcmind.cleaapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.NavEntry
import com.drcmind.cleaapp.ui.auth.login.LoginScreen
import com.drcmind.cleaapp.ui.auth.login.SignInScreen
import com.drcmind.cleaapp.ui.auth.splash.SplashScreen
import com.drcmind.cleaapp.ui.home.HomeScreen
import com.drcmind.cleaapp.ui.profile.ProfileScreen

@Composable
fun AppNavigation(

) {
    val backStack = remember { mutableStateListOf<Any>(AppDestination.Splash) }
    
    NavDisplay(
        backStack = backStack,
    ) { destination ->
        when (destination) {
            is AppDestination.Splash -> NavEntry(destination) {
                SplashScreen(
                    onNavigate = { dest ->
                        backStack.clear()
                        backStack.add(dest)
                    }
                )
            }
            is AppDestination.Login -> NavEntry(destination) {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(AppDestination.Home)
                    },
                    onSignInNavigation = {
                        backStack.add(AppDestination.SignIn)
                    }
                )
            }
            is AppDestination.SignIn -> NavEntry(destination) {
                SignInScreen(
                    onBackToLogin = {
                        backStack.removeLast()
                    }
                )
            }
            is AppDestination.Home -> NavEntry(destination) {
                HomeScreen(
                    onNavigateToProfile = {
                        backStack.add(AppDestination.Profile)
                    }
                )
            }
            is AppDestination.Profile -> NavEntry(destination) {
                ProfileScreen(
                    onLogoutSuccess = {
                        backStack.clear()
                        backStack.add(AppDestination.Login)
                    },
                    onBack = {
                        backStack.removeLast()
                    }
                )
            }
            else -> NavEntry(destination) { }
        }
    }
}
