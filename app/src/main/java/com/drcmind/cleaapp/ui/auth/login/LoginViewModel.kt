package com.drcmind.cleaapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drcmind.cleaapp.domain.model.AuthResult
import com.drcmind.cleaapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, fieldErrors = it.fieldErrors - "email") }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, fieldErrors = it.fieldErrors - "password") }
    }

    fun login() {
        val currentState = _state.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.update { it.copy(globalError = "Veuillez remplir tous les champs") }
            return
        }
        if (!EMAIL_REGEX.matches(currentState.email)) {
            _state.update { it.copy(fieldErrors = it.fieldErrors + ("email" to listOf("Adresse email invalide"))) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, globalError = null, fieldErrors = emptyMap()) }
            
            when (val result = repository.login(currentState.email, currentState.password)) {
                is AuthResult.Success<*> -> {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is AuthResult.Error -> {
                    _state.update { 
                        it.copy(
                            isLoading = false, 
                            globalError = if (result.fieldErrors.isEmpty()) result.message else null,
                            fieldErrors = result.fieldErrors
                        ) 
                    }
                }
                else -> {}
            }
        }
    }
}
