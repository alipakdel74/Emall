package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.db.doa.InvoiceDao
import com.majazeh.emall.utils.AppPreferences

class ShoppingCartRepository(private val api: DataApi, private val dao: InvoiceDao) :
    BaseDataSource() {
    suspend fun getProductDB() = dao.getAll()
    suspend fun getEmallPrice() = dao.getEmallPrice()
    suspend fun getMarketPrice() = dao.getMarketPrice()
    suspend fun getAmountNumber() = dao.getAllNumber()
    suspend fun deleteCartDB(id: String) = dao.deleteInvoice(id)
    suspend fun addProduct(id: String, count: String) =
        getResult { api.addCart(AppPreferences.auth, id, count) }
}