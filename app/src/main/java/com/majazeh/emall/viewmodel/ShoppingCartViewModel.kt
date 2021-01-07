package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.ShoppingCart
import com.majazeh.emall.pattern.ExplodeSingleton

class ShoppingCartViewModel : BaseViewModel() {

    private val _cart = MutableLiveData<ShoppingCart>()
    val cart: LiveData<ShoppingCart> = _cart

    private val data = ExplodeSingleton.getInstance()

    init {
        data?.apply {
            explode?.apply {
                cart?.apply {
                    _cart.value = this
                }
            }
        }
    }

    fun closeCart(){

    }

}