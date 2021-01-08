package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.ImageUrl
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.data.api.response.ProductValue
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

    private val data = ExplodeSingleton.getInstance()

    init {
        shoppingCart()
    }

    fun closeCart(id: String) {
        data?.apply {
            explode?.apply {
                if (version.login)
                    cart?.apply {
                        deleteCart(id)
                        return
                    }
            }
        }
        deleteCartDB(id)
    }

    private fun deleteCart(id: String) {
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

    private fun deleteCartDB(id: String) {
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

    fun shoppingCart() {
        data?.apply {
            explode?.apply {
                if (version.login)
                    cart?.apply {
                        _cart.value = this
                    }
                else shoppingCartDB()
            }
        }
    }

    private fun shoppingCartDB() {
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.getProductDB()
            }

            val total = withContext(Dispatchers.IO) {
                repo.getTotal()
            }
            val amount = withContext(Dispatchers.IO) {
                repo.getAmountAll()
            }

            res?.apply {
                val products = mutableListOf<Product>()
                forEach {
                    products.add(
                        Product(
                            it.productId,
                            it.title,
                            ProductValue(0, it.category),
                            ProductValue(0, it.brand),
                            it.market_price,
                            it.emall_price,
                            it.discount,
                            it.barcode,
                            it.unit_type,
                            it.unit,
                            it.status,
                            it.quota,
                            it.description,
                            ImageUrl(it.imageUrl, "", "", "")
                        )
                    )
                }
                val shop =
                    ShoppingCart("0", 0, total ?: 0, 0f, "", "", 0, amount ?: 0, "", "", products)
                _cart.value = shop
            }
            _loading.value = false
        }
    }

}