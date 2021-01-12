package com.majazeh.emall.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ali74.libkot.BindingActivity
import com.majazeh.emall.R
import com.majazeh.emall.databinding.ContentBinding

class ContentActivity : BindingActivity<ContentBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
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