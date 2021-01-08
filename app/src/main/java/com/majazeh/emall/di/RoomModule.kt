package com.majazeh.emall.di

import com.majazeh.emall.data.db.ProductDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { ProductDataBase.getInstance(androidApplication()) }
    single(createdAtStart = true) { get<ProductDataBase>().getProductDao() }
}