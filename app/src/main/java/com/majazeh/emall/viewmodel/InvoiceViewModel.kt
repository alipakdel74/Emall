package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.Invoice
import com.majazeh.emall.data.api.response.ShoppingCart
import com.majazeh.emall.repository.InvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InvoiceViewModel(private val repo: InvoiceRepository) : BaseViewModel() {

    private val _invoice = MutableLiveData<MutableList<Invoice>>()
    val invoice: LiveData<MutableList<Invoice>> = _invoice

    private val _invoiceDetail = MutableLiveData<ShoppingCart>()
    val invoiceDetail: LiveData<ShoppingCart> = _invoiceDetail

    init {
        getInvoice(1)
    }

    fun getInvoice(page: Int) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.invoices(page)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok)
                        _invoice.value = data
                    else _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    fun getInvoiceDetail(id: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.detailInvoice(id)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok)
                        _invoiceDetail.value = data
                    else _message.value = message_text
                }
            }
            _loading.value = false
        }
    }
}