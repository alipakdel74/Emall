package com.majazeh.emall.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.ali74.libkot.utils.formatMobile
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.LoginRepository
import com.majazeh.emall.utils.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repo: LoginRepository) : BaseViewModel() {

    private val _dataLogin = MutableLiveData<Boolean>()
    val dataLogin: LiveData<Boolean> = _dataLogin

    val mName = ObservableField<String>()
    val mUsername = ObservableField<String>()
    val mPassword = ObservableField<String>()

    private val data = ExplodeSingleton.getInstance()

    fun login() {
        val username = mUsername.get() ?: ""
        val password = mPassword.get() ?: ""

        if (username.isEmpty()) {
            _message.value = "أدخل رقم هاتفك المحمول"
            return
        }
        if (!formatMobile(username)) {
            _message.value = "تم إدخال رقم الهاتف المحمول غير صحيح"
            return
        }
        if (password.isEmpty()) {
            _message.value = "ادخل رقمك السري"
            return
        }

        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.login(username, password)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> {
                    res.data?.apply {
                        if (isOk) {
                            AppPreferences.auth = "Bearer " + data.token
                            getExplode()
                        } else
                            _message.value = messageText
                    }
                }
            }
        }
        _loading.value = false
    }

    fun register(){
        val name = mName.get() ?: ""
        val username = mUsername.get() ?: ""
        val password = mPassword.get() ?: ""

        if (name.isEmpty()) {
            _message.value = "أدخل اسم"
            return
        }
        if (username.isEmpty()) {
            _message.value = "أدخل رقم هاتفك المحمول"
            return
        }
        if (!formatMobile(username)) {
            _message.value = "تم إدخال رقم الهاتف المحمول غير صحيح"
            return
        }
        if (password.isEmpty()) {
            _message.value = "ادخل رقمك السري"
            return
        }

        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.register(name,username, password)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> {
                    res.data?.apply {
                        if (isOk) {
                            AppPreferences.auth = "Bearer " + data.token
                            getExplode()
                        } else
                            _message.value = messageText
                    }
                }
            }
        }
        _loading.value = false
    }

    private fun getExplode() {
        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.explode(AppPreferences.auth)
            }

            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok) {
                        data?.explode = this
                        _dataLogin.value = is_ok
                    } else _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

}