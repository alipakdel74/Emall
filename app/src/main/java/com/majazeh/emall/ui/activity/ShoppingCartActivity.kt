package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.ShoppingCartBinding
import com.majazeh.emall.ui.adapter.ShoppingCartAdapter
import com.majazeh.emall.viewmodel.ShoppingCartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingCartActivity : BindingActivity<ShoppingCartBinding>() {

    private val vm by viewModel<ShoppingCartViewModel>()

    private var isLogin = false
    private var refreshList = false

    override fun getLayoutResId(): Int = R.layout.activity_shopping_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        binding.btnConfirm.setOnClickListener {
            if (isLogin)
                vm.addPreInvoice()
            else SnackBarBuilder(getString(R.string.invalidLogin))
                .setDuration(3000)
                .setActionText(getString(R.string.signIn), R.color.primaryColor)
                .setAction {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .show(this)
        }

        vm.addPreInvoice.observe(this, {
            if (it) {
                startActivity(Intent(this, PreInvoiceActivity::class.java))
                finish()
            }
        })

        vm.cart.observe(this, {
            binding.cart = it

            if (it.details.isNullOrEmpty()) {
                binding.txtNull.visibility = View.VISIBLE
                binding.constrainDetail.visibility = View.GONE
                binding.rclShopping.visibility = View.GONE
            } else {
                binding.txtNull.visibility = View.GONE
                binding.constrainDetail.visibility = View.VISIBLE
                binding.rclShopping.visibility = View.VISIBLE
                if (binding.rclShopping.adapter == null)
                    binding.rclShopping.adapter = ShoppingCartAdapter(this, it.details, vm)
                else {
                    if (refreshList)
                        (binding.rclShopping.adapter as ShoppingCartAdapter).refresh(it.details)
                }
            }

        })

        vm.isLogin.observe(this, {
            isLogin = it
        })

        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })
        vm.closeCart.observe(this, {
            refreshList = true
            if (it) vm.shoppingCartDB()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_menu).setIcon(R.drawable.ic_back)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_menu) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1006)
            if (data!!.getBooleanExtra("changeData", false)) {
                val position = data.getIntExtra("position", -1)
                val count = data.getIntExtra("count", 1)
                refreshList = false
                vm.shoppingCartDB()
                (binding.rclShopping.adapter as ShoppingCartAdapter).refreshItem(position, count)
            }
    }

}