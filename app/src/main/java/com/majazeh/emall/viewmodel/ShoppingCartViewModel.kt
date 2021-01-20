package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.google.gson.Gson
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.data.api.response.ShoppingCart
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.ShoppingCartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingCartViewModel(private val repo: ShoppingCartRepository) : BaseViewModel() {

    private val _cart = MutableLiveData<ShoppingCart>()
    val cart: LiveData<ShoppingCart> = _cart

    private val _closeCart = MutableLiveData<Boolean>()
    val closeCart: LiveData<Boolean> = _closeCart

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _addPreInvoice = MutableLiveData<Boolean>()
    val addPreInvoice: LiveData<Boolean> = _addPreInvoice

    private val data = ExplodeSingleton.getInstance()

    init {
        data?.apply {
            explode?.apply {
                _isLogin.value = version.login
            }
        }

        shoppingCartDB()
    }

    fun deleteCartDB(id: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.deleteCartDB(id)
            }
            if (res != 0)
                _closeCart.value = true
            _loading.value = false
        }
    }

    fun shoppingCartDB() {
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.getProductDB()
            }
            val emallParice = withContext(Dispatchers.IO) {
                repo.getEmallPrice()
            }
            val marketPrice = withContext(Dispatchers.IO) {
                repo.getMarketPrice()
            }
            val total = withContext(Dispatchers.IO) {
                repo.getAmountNumber()
            }

            res?.let {
                val invoices = mutableListOf<PreInvoice>()
                res.forEach {
                    val product = Gson().fromJson(it.product, Product::class.java)
                    val invoice = PreInvoice(
                        it.id,
                        it.invoice_id,
                        product,
                        it.count,
                        it.market_price,
                        it.emall_price,
                        it.market_price * it.count,
                        it.emall_price * it.count,
                        it.discount,
                    )
                    invoices.add(invoice)
                }

                val shop = ShoppingCart(
                    "0",
                    marketPrice ?: 0,
                    emallParice ?: 0,
                    0,
                    "",
                    "",
                    invoices.size,
                    total ?: 0,
                    "",
                    "",
                    invoices
                )
                _cart.value = shop
            }
            _loading.value = false
        }
    }

    fun addPreInvoice() {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.getProductDB()
            }
            res?.let {
                res.forEach {
                    addCart(it.id, it.count)
                }
            }
            _addPreInvoice.value = true
            _loading.value = false
        }
    }

    private fun addCart(id: String, count: Int) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.addProduct(id, count)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (!is_ok) _message.value = message_text
                    else deleteCartDB(id)
                }
            }
            _loading.value = false
        }
    }

}