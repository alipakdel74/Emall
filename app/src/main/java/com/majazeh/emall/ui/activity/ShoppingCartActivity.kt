package com.majazeh.emall.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ali74.libkot.BindingActivity
import com.majazeh.emall.R
import com.majazeh.emall.databinding.ShoppingCartBinding
import com.majazeh.emall.model.ProductType
import com.majazeh.emall.ui.adapter.ProductAdapter
import com.majazeh.emall.viewmodel.ShoppingCartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingCartActivity : BindingActivity<ShoppingCartBinding>() {

    private val vm by viewModel<ShoppingCartViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_shopping_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        vm.cart.observe(this, {
            binding.cart = it
//            binding.rclShopping.adapter = ProductAdapter(it.details, ProductType.SHOPPING, vm)
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

}