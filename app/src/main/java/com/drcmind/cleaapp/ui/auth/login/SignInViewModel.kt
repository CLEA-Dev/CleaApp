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

class SignInViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name, fieldErrors = it.fieldErrors - "name") }
    }

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, fieldErrors = it.fieldErrors - "email") }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, fieldErrors = it.fieldErrors - "password") }
    }

    fun onPasswordConfirmationChange(passwordConfirmation: String) {
        _state.update { it.copy(passwordConfirmation = passwordConfirmation, fieldErrors = it.fieldErrors - "password_confirmation") }
    }

    fun register() {
        val currentState = _state.value
        if (currentState.name.isBlank() || currentState.email.isBlank() || 
            currentState.password.isBlank() || currentState.passwordConfirmation.isBlank()) {
            _state.update { it.copy(globalError = "Veuillez remplir tous les champs") }
            return
        }
        
        if (currentState.password != currentState.passwordConfirmation) {
            _state.update { it.copy(globalError = "Les mots de passe ne correspondent pas") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, globalError = null, fieldErrors = emptyMap()) }
            
            val result = repository.register(
                currentState.name, 
                currentState.email, 
                currentState.password, 
                currentState.passwordConfirmation
            )
            
            when (result) {
                is AuthResult.Success -> {
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
