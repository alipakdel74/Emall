package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class LoginRepository(private val api: DataApi) : BaseDataSource() {
    suspend fun login(username: String, password: String) =
        getResult { api.login(username, password) }

    suspend fun explode(auth: String) = getResult { api.explode(auth) }

}