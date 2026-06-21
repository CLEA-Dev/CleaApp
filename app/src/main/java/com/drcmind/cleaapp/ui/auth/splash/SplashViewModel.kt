package com.drcmind.cleaapp.ui.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drcmind.cleaapp.data.local.datastore.AuthDataStore
import com.drcmind.cleaapp.ui.navigation.AppDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<AppDestination>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            // Fake delay for splash screen branding
            delay(1500)

            val isOnboardingCompleted = authDataStore.isOnboardingCompleted.firstOrNull() ?: false
            val token = authDataStore.authToken.firstOrNull()

            val destination = when {
                !isOnboardingCompleted -> AppDestination.Onboarding
                token.isNullOrEmpty() -> AppDestination.Login
                else -> AppDestination.Home
            }
            _navigationEvent.emit(destination)
        }
    }
}
