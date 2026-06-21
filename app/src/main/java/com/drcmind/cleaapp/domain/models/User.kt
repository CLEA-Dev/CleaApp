package com.drcmind.cleaapp.domain.models

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val role: String? = null
)
