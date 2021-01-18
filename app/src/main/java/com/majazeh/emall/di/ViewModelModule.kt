package com.majazeh.emall.di

import com.majazeh.emall.repository.*
import com.majazeh.emall.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { IntroViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ShoppingCartViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { InvoiceViewModel(get()) }
    viewModel { MapViewModel(get()) }
}

val repositoryModule = module {
    single { SplashRepository(get()) }
    single { LoginRepository(get()) }
    single { MainRepository(get(), get()) }
    single { ProfileRepository(get()) }
    single { ShoppingCartRepository(get(), get()) }
    single { EditProfileRepository(get()) }
    single { DetailRepository(get(), get()) }
    single { InvoiceRepository(get()) }
    single { MapRepository(get()) }
}

