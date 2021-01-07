package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ali74.libkot.BindingActivity
import com.majazeh.emall.R
import com.majazeh.emall.databinding.IntroBinding
import com.majazeh.emall.ui.adapter.IntroPagerAdapter
import com.majazeh.emall.utils.AppPreferences
import com.majazeh.emall.viewmodel.IntroViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : BindingActivity<IntroBinding>() {

    private val introViewModel by viewModel<IntroViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_intro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        introViewModel.intro.observe(this, { intro ->
            binding.viewpagerIntro.adapter = IntroPagerAdapter(intro)
            binding.wormDotsIndicator.setViewPager(binding.viewpagerIntro)

            binding.viewpagerIntro.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    if (position == intro.size - 1)
                        binding.floatNext.text = "البداية"
                    else
                        binding.floatNext.text = "التالي"
                    if (position == 0)
                        binding.floatPrev.visibility = View.GONE
                    else binding.floatPrev.visibility = View.VISIBLE
                }

                override fun onPageSelected(position: Int) {}

                override fun onPageScrollStateChanged(state: Int) {}

            })

        })

        binding.floatNext.setOnClickListener {
            if (binding.floatNext.text == "البداية") {
                AppPreferences.firstRun = true
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            } else
                binding.viewpagerIntro.currentItem += 1
        }

        binding.floatPrev.setOnClickListener {
            binding.viewpagerIntro.currentItem -= 1
        }
    }
}