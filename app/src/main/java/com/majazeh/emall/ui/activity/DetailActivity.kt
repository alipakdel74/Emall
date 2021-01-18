package com.majazeh.emall.ui.activity

import android.os.Bundle
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.databinding.DetailBinding
import com.majazeh.emall.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BindingActivity<DetailBinding>() {

    private val vm by viewModel<DetailViewModel>()
    private var count = 1
    private var product: Product? = null

    override fun getLayoutResId(): Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        product = intent.getParcelableExtra("data")
        if (product == null) finish()

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
            vm.addCart(product!!.id, count, product!!)
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
    }

}