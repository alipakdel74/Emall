package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.ShoppingCart
import com.majazeh.emall.repository.PreInvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreInvoiceViewModel(private val repo: PreInvoiceRepository) : BaseViewModel() {

    private val _cart = MutableLiveData<ShoppingCart>()
    val cart: LiveData<ShoppingCart> = _cart

    private val _closeCart = MutableLiveData<Boolean>()
    val closeCart: LiveData<Boolean> = _closeCart

    init {
        cart()
    }

    fun cart() {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.preInvoice()
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok)
                        _cart.value = data
                    else _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    fun closeCart(id: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.deleteCart(id)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    _closeCart.value = is_ok
                    _message.value = message_text
                }
            }

            _loading.value = false
        }
    }

}