package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class MapRepository(private val api: DataApi) : BaseDataSource() {
    suspend fun cart() = getResult { api.cart(AppPreferences.auth) }
    suspend fun closeCart(location: String) =
        getResult { api.closeCart(AppPreferences.auth, location) }
}