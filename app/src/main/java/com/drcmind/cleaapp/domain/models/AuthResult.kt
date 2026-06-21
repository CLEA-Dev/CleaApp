package com.drcmind.cleaapp.domain.models

sealed interface AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>
    data class Error(
        val message: String? = null,
        val fieldErrors: Map<String, List<String>> = emptyMap()
    ) : AuthResult<Nothing>
    data object Loading : AuthResult<Nothing>
}
