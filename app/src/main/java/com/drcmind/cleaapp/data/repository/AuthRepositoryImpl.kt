package com.drcmind.cleaapp.data.repository

import com.drcmind.cleaapp.data.local.datastore.AuthDataStore
import com.drcmind.cleaapp.data.remote.api.AuthApi
import com.drcmind.cleaapp.data.remote.dto.*
import com.drcmind.cleaapp.domain.model.AuthResult
import com.drcmind.cleaapp.domain.model.User
import com.drcmind.cleaapp.domain.repository.AuthRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val dataStore: AuthDataStore
) : AuthRepository {

    override suspend fun getCsrfToken(): String = api.getCsrfToken()

    override suspend fun login(email: String, password: String): AuthResult<Unit> {
        return try {
            val csrfToken = api.getCsrfToken()
            val response = api.login(LoginRequestDto(email, password), csrfToken)
            
            if (response.status == HttpStatusCode.OK) {
                val authResponse = response.body<AuthResponseDto>()
                if (authResponse.accessToken != null) {
                    dataStore.saveToken(authResponse.accessToken)
                    AuthResult.Success(Unit)
                } else {
                    AuthResult.Error("Le serveur n'a pas renvoyé de jeton d'accès.")
                }
            } else {
                handleResponseError(response)
            }
        } catch (e: Exception) {
            AuthResult.Error("Échec de la connexion : ${e.message}")
        }
    }

    override suspend fun register(name: String, email: String, password: String, passwordConfirmation: String): AuthResult<Unit> {
        return try {
            val csrfToken = api.getCsrfToken()
            val response = api.register(RegisterRequestDto(name, email, password, passwordConfirmation), csrfToken)
            if (response.status == HttpStatusCode.OK || response.status == HttpStatusCode.Created) {
                val authResponse = response.body<AuthResponseDto>()
                authResponse.accessToken?.let { dataStore.saveToken(it) }
                AuthResult.Success(Unit)
            } else {
                handleResponseError(response)
            }
        } catch (e: Exception) {
            AuthResult.Error("Échec de l'inscription.")
        }
    }

    override suspend fun getUser(): AuthResult<User> {
        return try {
            val token = dataStore.authToken.firstOrNull() ?: ""
            val userDto = api.getUser(token)
            AuthResult.Success(User(id = userDto.id, name = userDto.name, email = userDto.email, role = userDto.role))
        } catch (e: Exception) {
            AuthResult.Error("Session expirée.")
        }
    }

    override suspend fun logout(): AuthResult<Unit> {
        return try {
            val token = dataStore.authToken.firstOrNull() ?: ""
            val csrfToken = api.getCsrfToken()
            api.logout(token, csrfToken)
            dataStore.clearToken()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            dataStore.clearToken()
            AuthResult.Success(Unit)
        }
    }

    override suspend fun updateProfile(name: String, email: String): AuthResult<Unit> {
        return try {
            val token = dataStore.authToken.firstOrNull() ?: ""
            val csrfToken = api.getCsrfToken()
            val response = api.updateProfile(UpdateProfileRequestDto(name, email), token, csrfToken)
            if (response.status == HttpStatusCode.OK) AuthResult.Success(Unit) else handleResponseError(response)
        } catch (e: Exception) {
            AuthResult.Error("Erreur de mise à jour.")
        }
    }

    override suspend fun updatePassword(currentPassword: String, newPassword: String, newPasswordConfirmation: String): AuthResult<Unit> {
        return try {
            val token = dataStore.authToken.firstOrNull() ?: ""
            val csrfToken = api.getCsrfToken()
            val response = api.updatePassword(UpdatePasswordRequestDto(currentPassword, newPassword, newPasswordConfirmation), token, csrfToken)
            if (response.status == HttpStatusCode.OK) AuthResult.Success(Unit) else handleResponseError(response)
        } catch (e: Exception) {
            AuthResult.Error("Erreur mot de passe.")
        }
    }

    override suspend fun isLoggedIn(): Boolean = dataStore.authToken.firstOrNull() != null

    private suspend fun handleResponseError(response: HttpResponse): AuthResult.Error {
        val body = try { response.bodyAsText() } catch (e: Exception) { "" }
        return try {
            val json = Json.parseToJsonElement(body).jsonObject
            val errors = json["errors"]?.jsonObject
            val fieldErrors = mutableMapOf<String, List<String>>()
            errors?.forEach { (k, v) -> 
                if (v is kotlinx.serialization.json.JsonArray) {
                    fieldErrors[k] = v.map { it.jsonPrimitive.content }
                } else {
                    fieldErrors[k] = listOf(v.jsonPrimitive.content)
                }
            }
            AuthResult.Error(json["message"]?.jsonPrimitive?.content ?: "Données invalides", fieldErrors)
        } catch (e: Exception) {
            AuthResult.Error("Erreur serveur (${response.status.value})")
        }
    }
}
