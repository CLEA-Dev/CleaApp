package com.drcmind.cleaapp.domain.repository

import com.drcmind.cleaapp.domain.models.AuthResult
import com.drcmind.cleaapp.domain.models.User

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<Unit>
    suspend fun register(name: String, email: String, password: String, passwordConfirmation: String): AuthResult<Unit>
    suspend fun logout(): AuthResult<Unit>
    suspend fun getUser(): AuthResult<User>
    suspend fun updateProfile(name: String, email: String): AuthResult<Unit>
    suspend fun updatePassword(currentPassword: String, newPassword: String, newPasswordConfirmation: String): AuthResult<Unit>
    suspend fun isLoggedIn(): Boolean
    suspend fun getCsrfToken(): String
}
