package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.core.widget.doOnTextChanged
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.ali74.libkot.utils.hideKeyboard
import com.majazeh.emall.R
import com.majazeh.emall.databinding.LoginBinding
import com.majazeh.emall.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BindingActivity<LoginBinding>() {

    private val vm by viewModel<LoginViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.vm = vm

        binding.edtMobileNumber.doOnTextChanged { text, _, _, _ ->
            vm.mUsername.set(text.toString().trim())
        }
        binding.edtPassword.doOnTextChanged { text, _, _, _ ->
            vm.mPassword.set(text.toString().trim())
        }

        var flag = false
        binding.inputPassword.setStartIconOnClickListener {
            runOnUiThread {
                if (!flag) {
                    binding.inputPassword.setStartIconDrawable(R.drawable.ic_eye_visibility)
                    binding.edtPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    flag = true
                } else {
                    binding.edtPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.inputPassword.setStartIconDrawable(R.drawable.ic_eye_invisible)
                    flag = false
                }
                binding.edtPassword.setSelection(binding.edtPassword.text!!.length)
            }
        }

        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })
        vm.dataLogin.observe(this, {
            if (it)
                onBackPressed()
        })
        vm.message.observe(this, {
            hideKeyboard(this)
            SnackBarBuilder(it).show(this)
        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}