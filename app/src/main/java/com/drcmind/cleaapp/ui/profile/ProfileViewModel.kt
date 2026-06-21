package com.drcmind.cleaapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drcmind.cleaapp.domain.models.AuthResult
import com.drcmind.cleaapp.domain.models.User
import com.drcmind.cleaapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.getUser()) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(user = result.data, isLoading = false) }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    fun updateProfile(name: String, email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdating = true, updateSuccess = false, error = null) }
            when (val result = repository.updateProfile(name, email)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isUpdating = false, updateSuccess = true) }
                    loadUserProfile()
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isUpdating = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    fun updatePassword(current: String, new: String, confirm: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUpdating = true, updateSuccess = false, error = null) }
            when (val result = repository.updatePassword(current, new, confirm)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isUpdating = false, updateSuccess = true) }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isUpdating = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val error: String? = null,
    val updateSuccess: Boolean = false,
    val isLoggedOut: Boolean = false
)
