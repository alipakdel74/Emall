package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.core.widget.doOnTextChanged
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.EditProfileBinding
import com.majazeh.emall.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : BindingActivity<EditProfileBinding>() {

    private val vm by viewModel<EditProfileViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_edit_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.vm = vm

        vm.me.observe(this, {
            binding.user = it
        })

        binding.edtAddress.doOnTextChanged { text, _, _, _ ->
            vm.mAddress.set(text.toString().trim())
        }
        binding.edtEmail.doOnTextChanged { text, _, _, _ ->
            vm.mEmail.set(text.toString().trim())
        }
        binding.edtMobileNumber.doOnTextChanged { text, _, _, _ ->
            vm.mNumber.set(text.toString().trim())
        }
        binding.edtName.doOnTextChanged { text, _, _, _ ->
            vm.mName.set(text.toString().trim())
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
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

        vm.edit.observe(this, {
            val intent = Intent()
            intent.putExtra("EditUser", it)
            setResult(RESULT_OK, intent)
            finish()
        })

    }

}