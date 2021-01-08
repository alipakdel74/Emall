package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.data.db.doa.ProductDao
import com.majazeh.emall.data.db.entity.ProductDB
import com.majazeh.emall.utils.AppPreferences

class DetailRepository(private val api: DataApi, private val dao: ProductDao) : BaseDataSource() {
    suspend fun addProduct(id: String, count: String) =
        getResult { api.addCart(AppPreferences.auth, id, count) }

    suspend fun addProductDB(product: Product) = dao.setProduct(ProductDB.to(product))
}