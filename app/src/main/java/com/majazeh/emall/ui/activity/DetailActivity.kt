package com.majazeh.emall.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.ali74.libkot.utils.drawableToBitmap
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.databinding.DetailBinding
import com.majazeh.emall.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DetailActivity : BindingActivity<DetailBinding>() {

    private val vm by viewModel<DetailViewModel>()
    private var count = 1
    private var changeData = false

    override fun getLayoutResId(): Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this

        var product = intent.getParcelableExtra<Product>("data")
        val preInvoice = intent.getParcelableExtra<PreInvoice>("preInvoice")

        if (product == null && preInvoice == null) finish()

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        if (intent.hasExtra("preInvoice")) {
            vm.countProduct(preInvoice?.count ?: 1)
            product = preInvoice?.product
        } else
            vm.getNumber(product?.id ?: "")

        binding.item = product

        binding.ivAdd.setOnClickListener {
            if (count > 100)
                return@setOnClickListener
            count += 1
            vm.countProduct(count)
        }

        binding.ivMinute.setOnClickListener {
            if (count == 1)
                return@setOnClickListener
            count -= 1
            vm.countProduct(count)
        }

        binding.btnAddToCart.setOnClickListener {
//            if (intent.hasExtra("preInvoice"))
//                vm.addCart(preInvoice?.product?.id ?: "", count)
//            else
//                vm.addCartDB(product!!, count)
//            changeData = true

            if (intent.hasExtra("preInvoice")) {
                vm.addCart(preInvoice?.product?.id ?: "", count)
                vm.addCartDB(preInvoice?.product!!, count)
            } else {
                vm.addCart(product?.id ?: "", count)
                vm.addCartDB(product!!, count)
            }
            changeData = true
        }
        binding.ivShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, binding.txtProduct.text.toString().trim())
            sendIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView())
            sendIntent.type = "image/*"
            startActivity(sendIntent)
        }

        vm.count.observe(this, {
            count = it
            binding.txtAmount.text = it.toString()
        })

        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })
        vm.toast.observe(this, {
            SnackBarBuilder(getString(it)).show(this)
        })
        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })
        vm.addCart.observe(this, {
            if (it) onBackPressed()
        })

        vm.gotoLogin.observe(this, {
            if (it) {
                SnackBarBuilder(getString(R.string.invalidLogin))
                    .setDuration(3000)
                    .setActionText(getString(R.string.signIn), R.color.primaryColor)
                    .setAction {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    .show(this)
            }
        })
    }

    private fun getBitmapFromView(): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(this.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")
            val out = FileOutputStream(file)
            drawableToBitmap(binding.ivProduct.drawable)?.compress(
                Bitmap.CompressFormat.JPEG,
                90,
                out
            )
            out.close()
            bmpUri = Uri.fromFile(file)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    override fun onBackPressed() {
        if (changeData) {
            val intent = Intent()
            intent.putExtra("changeData", changeData)
            intent.putExtra("position", this.intent.getIntExtra("position", -1))
            intent.putExtra("count", count)
            setResult(1006, intent)
        }
        super.onBackPressed()
    }

}