package com.majazeh.emall.di

import com.majazeh.emall.repository.LoginRepository
import com.majazeh.emall.repository.MainRepository
import com.majazeh.emall.repository.SplashRepository
import com.majazeh.emall.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { IntroViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ShoppingCartViewModel() }
}

val repositoryModule = module {
    single { SplashRepository(get()) }
    single { LoginRepository(get()) }
    single { MainRepository(get()) }
}

