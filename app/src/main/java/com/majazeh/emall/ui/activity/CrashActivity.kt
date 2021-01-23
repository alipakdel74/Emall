package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.crashlytics.CustomActivityOnCrash
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.CrashErrorBinding

class CrashActivity : BindingActivity<CrashErrorBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_crash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = CustomActivityOnCrash.getConfigFromIntent(intent)

        binding.btnRestartCrashReport.setOnClickListener {
            CustomActivityOnCrash.restartApplication(this, config)
        }

        binding.btnCloseCrashReport.setOnClickListener {
            CustomActivityOnCrash.closeApplication(this, config)
        }

        val error = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this, intent)

        MaterialAlertDialogBuilder(this)
            .setTitle("AppError")
            .setNeutralButton("close") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("SendError") { dialog, _ ->
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_EMAIL, "alipakdel74.ap@gmail.com")
                intent.putExtra(Intent.EXTRA_SUBJECT, error.BuildVersion +"--" +error.Device)
                intent.putExtra(Intent.EXTRA_TEXT, error.StackTrace)
                startActivity(Intent.createChooser(intent, "Send Email"))
                dialog.dismiss()
            }
            .setMessage(error.UserAction + "\n\n" + error.StackTrace)
            .create()
            .show()

    }

}