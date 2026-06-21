package com.drcmind.cleaapp.di

import com.drcmind.cleaapp.data.local.datastore.AuthDataStore
import com.drcmind.cleaapp.data.remote.api.AuthApi
import com.drcmind.cleaapp.data.repository.AuthRepositoryImpl
import com.drcmind.cleaapp.domain.repository.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { AuthDataStore(androidContext()) }


    single { AuthApi(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
