package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.data.api.response.ProfileMe
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val repo: ProfileRepository) : BaseViewModel() {

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _me = MutableLiveData<ProfileMe>()
    val me: LiveData<ProfileMe> = _me

    private val data = ExplodeSingleton.getInstance()

    init {
        getUser()
    }

    fun getUser() {
        data?.apply {
            explode?.apply {
                me?.apply {
                    _me.value = this
                }
            }
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
                    _logout.value = is_ok
                    _message.value = message_text
                }
            }
            _loading.value = false
        }
    }
}