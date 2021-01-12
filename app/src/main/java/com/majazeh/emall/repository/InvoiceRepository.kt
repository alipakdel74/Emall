package com.majazeh.emall.repository

import com.ali74.libkot.core.BaseDataSource
import com.majazeh.emall.data.api.DataApi
import com.majazeh.emall.utils.AppPreferences

class InvoiceRepository(private val api: DataApi) : BaseDataSource() {
    suspend fun invoices(page: Int) = getResult { api.invoices(AppPreferences.auth, page) }
    suspend fun detailInvoice(id: String) =
        getResult { api.invoiceDetailShow(AppPreferences.auth, id) }
}