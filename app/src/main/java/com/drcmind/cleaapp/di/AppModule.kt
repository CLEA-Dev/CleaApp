package com.drcmind.cleaapp.di


import com.drcmind.cleaapp.data.local.datastore.AuthDataStore
import com.drcmind.cleaapp.data.remote.api.AuthApi
import com.drcmind.cleaapp.data.repository.AuthRepositoryImpl
import com.drcmind.cleaapp.domain.repository.AuthRepository
import com.drcmind.cleaapp.ui.auth.login.LoginViewModel
import com.drcmind.cleaapp.ui.auth.splash.SplashViewModel
import com.drcmind.cleaapp.ui.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Local
    single { AuthDataStore(androidContext()) }
    
    // API
    single { AuthApi(get()) }
    
    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    
    // ViewModels

    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
}
