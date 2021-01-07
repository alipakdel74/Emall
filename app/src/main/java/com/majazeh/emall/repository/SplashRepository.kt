package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class SplashRepository(private val api: DataApi) : BaseDataSource() {
    suspend fun explode() = getResult { api.explode(AppPreferences.auth) }
}