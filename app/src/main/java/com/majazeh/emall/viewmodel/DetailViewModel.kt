package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repo: DetailRepository) : BaseViewModel() {

    private val _toast = MutableLiveData<Int>()
    val toast: LiveData<Int> = _toast

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    private val _addCart = MutableLiveData<Boolean>()
    val addCart: LiveData<Boolean> = _addCart

    fun countProduct(count: Int) {
        _count.value = count
    }

    fun addCart(id: String, count: Int) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.addProduct(id, count.toString())
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (!is_ok) _message.value = message_text
                    else _addCart.value = is_ok
                }
            }
            _loading.value = false
        }
    }

    fun addCartDB(product: Product, count: Int) {
        val invoice = PreInvoice(
            product.id,
            "",
            product,
            count,
            product.market_price,
            product.emall_price,
            0,
            0,
            product.discount
        )
        _loading.value = true
        try {
            launch {
                withContext(Dispatchers.IO) {
                    repo.addProductDB(invoice)
                }
                _loading.value = false
                _toast.value = R.string.messageAddToCart
            }
        } catch (e: Exception) {
            _loading.value = false
            _message.value = e.message!!
        }

    }

    fun getNumber(id: String) {
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.getNumber(id)
            }
            _count.value = if (res == 0 || res == null) 1 else res
        }
    }

}