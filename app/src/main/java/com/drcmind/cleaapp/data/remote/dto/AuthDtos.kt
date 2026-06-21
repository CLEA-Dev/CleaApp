package com.drcmind.cleaapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    val name: String,
    val email: String,
    @SerialName("role") val role: String? = null
)

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    @SerialName("password_confirmation") val passwordConfirmation: String
)

@Serializable
data class AuthResponseDto(
    val message: String? = null,
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("token_type") val tokenType: String? = null,
    val user: UserDto? = null
)

@Serializable
data class UpdateProfileRequestDto(
    val name: String,
    val email: String
)

@Serializable
data class UpdatePasswordRequestDto(
    @SerialName("current_password") val currentPassword: String,
    @SerialName("new_password") val newPassword: String,
    @SerialName("new_password_confirmation") val newPasswordConfirmation: String
)
