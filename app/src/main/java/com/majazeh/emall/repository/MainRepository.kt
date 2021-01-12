package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.data.db.doa.ProductDao
import com.majazeh.emall.data.db.entity.ProductDB
import com.majazeh.emall.utils.AppPreferences

class MainRepository(private var api: DataApi, private val dao: ProductDao) : BaseDataSource() {
    suspend fun products(page: Int, q: String, cat: Int, brand: String) =
        getResult { api.products(AppPreferences.auth, page, q, cat, brand) }

    suspend fun logout() = getResult { api.logOut(AppPreferences.auth) }
    suspend fun addProduct(id: String, count: String) =
        getResult { api.addCart(AppPreferences.auth, id, count) }

    suspend fun addProductDB(product: Product) = dao.setProduct(ProductDB.to(product))
    suspend fun request(title: String, desc: String) =
        getResult { api.requests(AppPreferences.auth, title, desc) }
}