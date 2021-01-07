package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.Version
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val repo: SplashRepository) : BaseViewModel() {

    private val _version = MutableLiveData<Version>()
    val version: LiveData<Version> = _version

    private var dataSingleton = ExplodeSingleton.getInstance()

    init {
        getExplode()
    }

    private fun getExplode() {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.explode()
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok) {
                        dataSingleton?.explode = this
                        _version.value = version
                    } else _message.value = message_text
                }
            }

            _loading.value = false
        }
    }

}