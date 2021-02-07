package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.db.doa.InvoiceDao
import com.majazeh.emall.utils.AppPreferences

class PreInvoiceRepository(private val api: DataApi, private val dao: InvoiceDao) :
    BaseDataSource() {
    suspend fun preInvoice() = getResult { api.cart(AppPreferences.auth) }
    suspend fun deleteCart(id: String) = getResult { api.deleteCart(AppPreferences.auth, id) }
    suspend fun deleteCartDB(id: String) = dao.deleteInvoice(id)

}