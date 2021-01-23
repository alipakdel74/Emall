package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.data.db.doa.InvoiceDao
import com.majazeh.emall.data.db.entity.InvoiceDB
import com.majazeh.emall.utils.AppPreferences

class DetailRepository(private val api: DataApi, private val dao: InvoiceDao) : BaseDataSource() {
    suspend fun addProduct(id: String, count: String) =
        getResult { api.addCart(AppPreferences.auth, id, count) }

    suspend fun addProductDB(invoice: PreInvoice) = dao.updateOrAdd(InvoiceDB.to(invoice))
    suspend fun getNumber(id: String) = dao.getNumber(id)
}