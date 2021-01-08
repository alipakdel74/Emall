package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class ProfileRepository(private val api: DataApi) : BaseDataSource() {
    suspend fun logout() = getResult { api.logOut(AppPreferences.auth) }
}