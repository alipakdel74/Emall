package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.ProfileBinding
import com.majazeh.emall.utils.AppPreferences
import com.majazeh.emall.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : BindingActivity<ProfileBinding>() {

    private val vm by viewModel<ProfileViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.vm = vm

        vm.me.observe(this, {
            binding.user = it
        })

        vm.logout.observe(this, {
            if (it) {
                AppPreferences.auth = ""
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

        binding.floatEdit.setOnClickListener {
            startActivityForResult(Intent(this, EditProfileActivity::class.java), 0)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val editUser = data!!.getBooleanExtra("EditUser", false)
            if (editUser)
                vm.getUser()
        }
    }
}