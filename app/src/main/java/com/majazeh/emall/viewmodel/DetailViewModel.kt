package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailViewModel(private val repo: DetailRepository) : BaseViewModel() {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    private val data = ExplodeSingleton.getInstance()

    fun countProduct(count: Int) {
        _count.value = count
    }

    fun addCart(id: String, count: String, product: Product) {
        data?.apply {
            explode?.apply {
                if (version.login) {
                    addCart(id, count)
                    return
                }
            }
        }
        addCart(product)
    }

    private fun addCart(id: String, count: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.addProduct(id, count)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    private fun addCart(product: Product) {
        _loading.value = true
        try {
            launch {
                withContext(Dispatchers.IO) {
                    repo.addProductDB(product)
                }
                _message.value = "تمت الإضافة إلى عربة التسوق"
                _loading.value = false
            }
        } catch (e: Exception) {
            _loading.value = false
            _message.value = e.message!!
        }

    }
}