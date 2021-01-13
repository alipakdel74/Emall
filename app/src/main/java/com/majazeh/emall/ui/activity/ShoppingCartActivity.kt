package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.ShoppingCartBinding
import com.majazeh.emall.model.CartType
import com.majazeh.emall.ui.adapter.ShoppingCartAdapter
import com.majazeh.emall.viewmodel.ShoppingCartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingCartActivity : BindingActivity<ShoppingCartBinding>() {

    private val vm by viewModel<ShoppingCartViewModel>()

    private var isLogin = false

    override fun getLayoutResId(): Int = R.layout.activity_shopping_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        binding.btnConfirm.setOnClickListener {
            if (isLogin)
                startActivityForResult(Intent(this, PreInvoiceActivity::class.java), 0)
            else SnackBarBuilder("تسجيل الدخول للشراء")
                .setDuration(3000)
                .setActionText("تسجیل الدخول", R.color.primaryColor)
                .setAction {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                .show(this)
        }

        vm.cart.observe(this, {
            binding.cart = it
            if (binding.rclShopping.adapter == null)
                binding.rclShopping.adapter = ShoppingCartAdapter(it.details, vm, CartType.SHOPPING)
            else {
                (binding.rclShopping.adapter as ShoppingCartAdapter).refresh(it.details)
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
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            if (data!!.getBooleanExtra("changeData", false))
                vm.shoppingCart()
    }

}