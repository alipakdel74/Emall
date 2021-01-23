package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.ShoppingCart
import com.majazeh.emall.databinding.PreInvoiceBinding
import com.majazeh.emall.ui.adapter.PreInvoiceAdapter
import com.majazeh.emall.viewmodel.PreInvoiceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreInvoiceActivity : BindingActivity<PreInvoiceBinding>() {

    private val vm by viewModel<PreInvoiceViewModel>()

    private lateinit var shoppingCart: ShoppingCart

    override fun getLayoutResId(): Int = R.layout.activity_pre_invoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

        vm.cart.observe(this, {
            binding.cart = it
            if (it.details.isNullOrEmpty()) {
                binding.txtNull.visibility = View.VISIBLE
                binding.linearDetail.visibility = View.GONE
                binding.btnConfirm.visibility = View.GONE
                binding.rclInvoice.visibility = View.GONE
            } else {
                shoppingCart = it
                binding.txtNull.visibility = View.GONE
                binding.linearDetail.visibility = View.VISIBLE
                binding.btnConfirm.visibility = View.VISIBLE
                binding.rclInvoice.visibility = View.VISIBLE
                if (binding.rclInvoice.adapter == null)
                    binding.rclInvoice.adapter = PreInvoiceAdapter(this, it.details, vm)
                else
                    (binding.rclInvoice.adapter as PreInvoiceAdapter).refresh(it.details)
            }
        })

        vm.closeCart.observe(this, {
            if (it) vm.cart()
        })

        binding.btnConfirm.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java)
                .putExtra("data", shoppingCart))
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_menu).setIcon(R.drawable.ic_back)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_menu) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1006)
            if (data!!.getBooleanExtra("changeData", false))
                vm.cart()
    }

}