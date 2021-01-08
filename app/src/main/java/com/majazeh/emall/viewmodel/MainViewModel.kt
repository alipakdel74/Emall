package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.Category
import com.majazeh.emall.data.api.response.ResponseApi
import com.majazeh.emall.data.api.response.ProductData
import com.majazeh.emall.data.api.response.ProfileMe
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private var repo: MainRepository) : BaseViewModel() {

    private val _categories = MutableLiveData<MutableList<Category>>()
    val categories: LiveData<MutableList<Category>> = _categories

    private val _posCategory = MutableLiveData<Int>()
    val posCategory: LiveData<Int> = _posCategory

    private val _productsData = MutableLiveData<ProductData>()
    val productsData: LiveData<ProductData> = _productsData

    private val _me = MutableLiveData<ProfileMe>()
    val me: LiveData<ProfileMe> = _me

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _logout = MutableLiveData<ResponseApi>()
    val responseApi: LiveData<ResponseApi> = _logout

    private val data = ExplodeSingleton.getInstance()

    init {
        data?.explode?.apply {
            _categories.value = categories
            _isLogin.value = version.login
            me?.apply {
                _me.value = this
            }
        }
    }

    fun subCategory(position: Int) {
        _posCategory.value = position
    }

    fun getProduct(page: Int, q: String, cat: Int, brand: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.products(page, q, cat, brand)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok)
                        _productsData.value = this
                    else _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    fun logout() {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.logout()
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    _logout.value = this
                    _message.value = message_text
                }
            }
            _loading.value = false
        }
    }


}