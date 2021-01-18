package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.repository.MapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel(private val repo: MapRepository) : BaseViewModel() {

    private val _closeCart = MutableLiveData<Boolean>()
    val closeCart: LiveData<Boolean> = _closeCart

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
                    _message.value = message_text
                }
            }
            _loading.value = false
        }
    }
}