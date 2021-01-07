package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class MainRepository(private var api: DataApi) : BaseDataSource() {
    suspend fun products(page: Int, q: String, cat: Int, brand: String) =
        getResult { api.products(AppPreferences.auth, page, q, cat, brand) }

    suspend fun logout() = getResult { api.logOut(AppPreferences.auth) }
}