package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.data.db.doa.InvoiceDao
import com.majazeh.emall.data.db.entity.InvoiceDB
import com.majazeh.emall.utils.AppPreferences

class MainRepository(private var api: DataApi, private val dao: InvoiceDao) : BaseDataSource() {
    suspend fun products(page: Int, q: String, cat: Int, brand: String) =
        getResult { api.products(AppPreferences.auth, page, q, cat, brand) }

    suspend fun logout() = getResult { api.logOut(AppPreferences.auth) }
    suspend fun getCountAll() = dao.getAllNumber()

    suspend fun addProductDB(invoice: PreInvoice) = dao.updateOrAdd(InvoiceDB.to(invoice), true)
    suspend fun request(title: String, desc: String) =
        getResult { api.requests(AppPreferences.auth, title, desc) }
}