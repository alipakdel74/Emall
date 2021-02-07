package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.ProfileMe
import com.majazeh.emall.data.api.response.ShoppingCart
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel(private val repo: MapRepository) : BaseViewModel() {

    private val _me = MutableLiveData<ProfileMe>()
    val me: LiveData<ProfileMe> = _me

    private val _cart = MutableLiveData<ShoppingCart>()
    val cart: LiveData<ShoppingCart> = _cart

    private val _closeCart = MutableLiveData<Boolean>()
    val closeCart: LiveData<Boolean> = _closeCart


    private val data = ExplodeSingleton.getInstance()

    init {
        data?.apply {
            explode?.apply {
                me?.apply {
                    _me.value = this
                }
            }
        }
    }

    fun cart(shoppingCart: ShoppingCart) {
        _cart.value = shoppingCart
    }

    fun closeCart(location: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.closeCart(location)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    _closeCart.value = is_ok
                    if (!is_ok)
                        _message.value = message_text
                    else clearDB()
                }
            }
            _loading.value = false
        }
    }

    private fun clearDB() {
        launch {
            withContext(Dispatchers.IO) {
                repo.clearDB()
            }
        }
    }
}