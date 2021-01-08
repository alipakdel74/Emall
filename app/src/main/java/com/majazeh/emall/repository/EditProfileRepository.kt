package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class EditProfileRepository(private val api: DataApi) : BaseDataSource() {
    suspend fun edit(
        name: String,
        number: String,
        email: String,
        address: String,
        password: String
    ) = getResult { api.editProfile(AppPreferences.auth, name, number, email, address, password) }
}