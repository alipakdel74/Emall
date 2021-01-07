package com.majazeh.emall.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.QuestionDialogBuilder
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.SplashBinding
import com.majazeh.emall.utils.AppPreferences
import com.majazeh.emall.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BindingActivity<SplashBinding>() {

    private val vm by viewModel<SplashViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

        vm.version.observe(this, {
//            if (it.android.force)
            if (!it.android.force)
                questionDialog(it.android.link)
            else {
                if (!AppPreferences.firstRun)
                    startActivity(Intent(this, IntroActivity::class.java))
                else
                    startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

    }

    private fun questionDialog(url: String) {
        QuestionDialogBuilder(this)
            .setBtnCancel("إلغاء", R.color.red)
            .setBtnConfirm("تحديث", R.color.green)
            .setMessage("حدث التطبيق")
            .setOnclickBtn({
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                finishAffinity()
            }, {
                finishAffinity()
            }).create().show()
    }
}