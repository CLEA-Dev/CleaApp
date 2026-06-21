package com.drcmind.cleaapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class TokenManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val CSRF_TOKEN_KEY = stringPreferencesKey("csrf_token")
    }

    val token: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }
    val csrfToken: Flow<String?> = context.dataStore.data.map { it[CSRF_TOKEN_KEY] }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun saveCsrfToken(token: String) {
        context.dataStore.edit { it[CSRF_TOKEN_KEY] = token }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
