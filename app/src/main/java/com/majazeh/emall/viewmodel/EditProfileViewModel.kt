package com.majazeh.emall.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseResult
import com.ali74.libkot.core.BaseViewModel
import com.ali74.libkot.utils.formatMobile
import com.majazeh.emall.data.api.response.ProfileMe
import com.majazeh.emall.pattern.ExplodeSingleton
import com.majazeh.emall.repository.EditProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileViewModel(private val repo: EditProfileRepository) : BaseViewModel() {

    private val _me = MutableLiveData<ProfileMe>()
    val me: LiveData<ProfileMe> = _me

    private val _edit = MutableLiveData<ProfileMe>()
    val edit: LiveData<ProfileMe> = _edit

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
            _message.value = ""
            return
        }
        if (number.isNullOrEmpty()) {
            _message.value = ""
            return
        }
        if (!formatMobile(number)) {
            _message.value = ""
            return
        }
        if (email.isNullOrEmpty()) {
            _message.value = ""
            return
        }
        if (address.isNullOrEmpty()) {
            _message.value = ""
            return
        }
        if (password.isNullOrEmpty()) {
            _message.value = ""
            return
        }

        _loading.value = true
        launch {
            val res = withContext(Dispatchers.IO) {
                repo.edit(name, number, email, address, password)
            }
            when (res.status) {
                BaseResult.Status.ERROR -> _message.value = res.message!!
                BaseResult.Status.SUCCESS -> res.data?.apply {
                    if (is_ok) {
                        val profile = ProfileMe(
                            data!!.explode!!.me!!.id,
                            name,
                            name,
                            email,
                            number,
                            number,
                            data.explode!!.me!!.avatar,
                            "",
                            "",
                            address
                        )
                        _edit.value = profile
                        setUser(profile)
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
                me?.apply {
                    _me.value = profileMe
                }
            }
        }
    }
}