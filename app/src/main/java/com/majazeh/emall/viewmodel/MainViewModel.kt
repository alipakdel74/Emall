package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.*
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private var repo: MainRepository) : BaseViewModel() {

    private val _toast = MutableLiveData<Int>()
    val toast: LiveData<Int> = _toast

    private val _categories = MutableLiveData<MutableList<Category>>()
    val categories: LiveData<MutableList<Category>> = _categories

    private val _posCategory = MutableLiveData<Int>()
    val posCategory: LiveData<Int> = _posCategory

    private val _productsData = MutableLiveData<MutableList<Product>>()
    val productsData: LiveData<MutableList<Product>> = _productsData

    private val _productsDataSearch = MutableLiveData<ProductData>()
    val productsDataSearch: LiveData<ProductData> = _productsDataSearch

    private val _me = MutableLiveData<ProfileMe?>()
    val me: LiveData<ProfileMe?> = _me

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _logout = MutableLiveData<ResponseApi>()
    val logout: LiveData<ResponseApi> = _logout

    private val _request = MutableLiveData<Boolean>()
    val request: LiveData<Boolean> = _request

    private val _lazyLoad = MutableLiveData<Boolean>()
    val lazyLoad: LiveData<Boolean> = _lazyLoad

    private val _counterProduct = MutableLiveData<Int>()
    val counterProduct: LiveData<Int> = _counterProduct

    private val data = ExplodeSingleton.getInstance()

    init {
        getUser()
        data?.explode?.apply {
            _categories.value = categories
        }
    }

    fun getUser() {
        data?.explode?.apply {
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
                        _productsData.value = data
                    else _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    fun getProductSearch(page: Int, q: String, cat: Int, brand: String) {
        _lazyLoad.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.products(page, q, cat, brand)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok)
                        _productsDataSearch.value = this
                    else _message.value = message_text
                }
            }
            _lazyLoad.value = false
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
                    data?.explode?.version?.login = false
                    data?.explode?.me = null
                    _me.value = null
                    _isLogin.value = false
                    _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    fun addCart(product: Product) {
        val invoice = PreInvoice(
            product.id,
            "",
            product,
            1,
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

                getCountAll()
            }
        } catch (e: Exception) {
            _loading.value = false
            _message.value = e.message!!
        }

    }

    fun getCountAll() {
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.getCountAll()
            }
            if (res == null)
                _counterProduct.value = 0
            else
                _counterProduct.value = res
        }
    }

    fun request(title: String, desc: String) {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.request(title, desc)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    _message.value = message_text
                    _request.value = is_ok
                }
            }

            _loading.value = false
        }

    }


}