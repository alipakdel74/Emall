package com.majazeh.emall.ui.activity

import android.os.Bundle
import com.ali74.libkot.BindingActivity
import com.majazeh.emall.R
import com.majazeh.emall.databinding.DetailBinding

class DetailActivity : BindingActivity<DetailBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.item = intent.getParcelableExtra("data")!!
    }

}