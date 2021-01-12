package com.majazeh.emall.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.button.MaterialButton
import com.majazeh.emall.R

class QuestionDialog(context: Context) : Dialog(context,R.style.CustomDialogTheme) {

    private val ivClose by lazy { findViewById<AppCompatImageView>(R.id.ivClose) }
    private val edtTitle by lazy { findViewById<AppCompatEditText>(R.id.edtTitle) }
    private val edtDesc by lazy { findViewById<AppCompatEditText>(R.id.edtDesc) }
    private val txtConfirm by lazy { findViewById<MaterialButton>(R.id.txtConfirm) }

    private lateinit var sendData: SendData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_question)
        window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        setCancelable(false)

        ivClose.setOnClickListener { dismiss() }

        txtConfirm.setOnClickListener {
            if (edtTitle.text.toString().trim().isEmpty() || edtDesc.text.toString().trim()
                    .isEmpty()
            )
                return@setOnClickListener
            sendData.data(edtTitle.text.toString().trim(), edtDesc.text.toString().trim())
        }

    }

    override fun onBackPressed() {
        dismiss()
    }

    interface SendData {
        fun data(title: String, desc: String)
    }

    fun sendData(sendData: SendData) {
        this.sendData = sendData
    }

}