package com.drcmind.cleaapp.ui.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val fieldErrors: Map<String, List<String>> = emptyMap()
)
