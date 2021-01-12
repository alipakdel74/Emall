package com.majazeh.emall.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.ali74.libkot.utils.formatMobile
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.ProfileMe
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.EditProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileViewModel(private val repo: EditProfileRepository) : BaseViewModel() {

    private val _me = MutableLiveData<ProfileMe>()
    val me: LiveData<ProfileMe> = _me

    private val _toast = MutableLiveData<Int>()
    val toast: LiveData<Int> = _toast

    private val _edit = MutableLiveData<Boolean>()
    val edit: LiveData<Boolean> = _edit

    val mName = ObservableField("")
    val mNumber = ObservableField("")
    val mEmail = ObservableField("")
    val mAddress = ObservableField("")
    val mPassword = ObservableField("")

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

    fun edit() {
        val name = mName.get()
        val number = mNumber.get()
        val email = mEmail.get()
        val address = mAddress.get()
        val password = mPassword.get()

        if (name.isNullOrEmpty()) {
            _toast.value = R.string.invalidName
            return
        }
        if (number.isNullOrEmpty()) {
            _toast.value = R.string.invalidUsername
            return
        }
        if (!formatMobile(number)) {
            _toast.value = R.string.invalidUsername
            return
        }
        if (!email.isNullOrEmpty() && !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
            _toast.value = R.string.invalidEmail
            return
        }
        if (!address.isNullOrEmpty() && address.length < 10) {
            _toast.value = R.string.invalidAddress
            return
        }
        if (password.isNullOrEmpty()) {
            _toast.value = R.string.invalidPassword
            return
        }

        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.edit(name, number, email ?: "", address ?: "", password)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok) {
                        setUser(data)
                        _edit.value = true
                    }
                    _message.value = message_text
                }
            }
            _loading.value = false
        }
    }

    private fun setUser(profileMe: ProfileMe) {
        data?.apply {
            explode?.apply {
                me = profileMe
            }
        }
    }
}