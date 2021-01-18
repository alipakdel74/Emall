package com.majazeh.emall.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.PreInvoiceBinding
import com.majazeh.emall.model.CartType
import com.majazeh.emall.ui.adapter.ShoppingCartAdapter
import com.majazeh.emall.viewmodel.ShoppingCartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreInvoiceActivity : BindingActivity<PreInvoiceBinding>() {

    private val vm by viewModel<ShoppingCartViewModel>()

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
                binding.cardDetail.visibility = View.GONE
            } else {
                binding.txtNull.visibility = View.GONE
                binding.cardDetail.visibility = View.VISIBLE
                if (binding.rclInvoice.adapter == null)
                    binding.rclInvoice.adapter =
                        ShoppingCartAdapter(it.details, vm, CartType.INVOICE)
                else
                    (binding.rclInvoice.adapter as ShoppingCartAdapter).refresh(it.details)
            }
        })
        vm.closeCart.observe(this, {
            if (it)
                vm.shoppingCart()
        })

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


}