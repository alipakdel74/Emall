package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.db.doa.InvoiceDao
import com.majazeh.emall.utils.AppPreferences

class MapRepository(private val api: DataApi, private val dao: InvoiceDao) : BaseDataSource() {
    suspend fun closeCart(location: String) =
        getResult { api.closeCart(AppPreferences.auth, location) }

    suspend fun clearDB() = dao.deleteAll()
}