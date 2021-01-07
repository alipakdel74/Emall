package com.majazeh.emall.di

import com.majazeh.emall.data.api.DataApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(DataApi::class.java) }
}