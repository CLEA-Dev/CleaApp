package com.drcmind.cleaapp.ui.auth.login

data class SignInState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val globalError: String? = null,
    val fieldErrors: Map<String, List<String>> = emptyMap()
)
