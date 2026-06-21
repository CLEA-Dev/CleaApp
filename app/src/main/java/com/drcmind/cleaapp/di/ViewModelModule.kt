package com.drcmind.cleaapp.di

import com.drcmind.cleaapp.ui.auth.login.LoginViewModel
import com.drcmind.cleaapp.ui.auth.login.SignInViewModel
import com.drcmind.cleaapp.ui.auth.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignInViewModel(get()) }
}
