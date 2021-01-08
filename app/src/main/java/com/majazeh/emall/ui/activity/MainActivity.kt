package com.majazeh.emall.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.MainBinding
import com.majazeh.emall.ui.adapter.CategoryAdapter
import com.majazeh.emall.ui.fragment.MainFragment
import com.majazeh.emall.utils.AppPreferences
import com.majazeh.emall.utils.ViewPagerAdapter
import com.majazeh.emall.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BindingActivity<MainBinding>() {

    private val vm by viewModel<MainViewModel>()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_user -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.nav_Content -> {

                }
                R.id.nav_Intro -> {
                    startActivity(Intent(this, IntroActivity::class.java))
                }
                R.id.nav_invoice -> {
                    startActivity(Intent(this, InvoiceActivity::class.java))
                }
                R.id.nav_exit -> {
                    SnackBarBuilder("هل تقوم بتسجيل الخروج من حسابك؟")
                        .setActionText("الخروج", R.color.black)
                        .setAction {
                            vm.logout()
                        }.setDuration(4000)
                        .show(this)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.END)
            return@setNavigationItemSelectedListener true
        }

        vm.isLogin.observe(this, {
            if (it) {
                binding.navView.menu.findItem(R.id.nav_user).isVisible = false
                binding.navView.menu.findItem(R.id.nav_invoice).isVisible = true
                binding.navView.menu.findItem(R.id.nav_exit).isVisible = true
                binding.navView.menu.findItem(R.id.nav_profile).isVisible = true
            } else {
                binding.navView.menu.findItem(R.id.nav_user).isVisible = true
                binding.navView.menu.findItem(R.id.nav_invoice).isVisible = false
                binding.navView.menu.findItem(R.id.nav_exit).isVisible = false
                binding.navView.menu.findItem(R.id.nav_profile).isVisible = false
            }
        })
        vm.me.observe(this, {
            if (!it.name.isNullOrEmpty())
                binding.navView.getHeaderView(0)
                    .findViewById<AppCompatTextView>(R.id.fullName).text =
                    it.name.plus(" ").plus(it.nikname)
            binding.navView.getHeaderView(0).findViewById<AppCompatTextView>(R.id.username).text =
                it.mobile
        })

        vm.categories.observe(this, {
            binding.appBarMain.rclCategory.adapter = CategoryAdapter(it, vm)
            val fragments = mutableListOf<Fragment>()
            for (f in it)
                fragments.add(MainFragment().newInstance(f))

            binding.appBarMain.viewpager.adapter =
                ViewPagerAdapter(supportFragmentManager, fragments)
            binding.appBarMain.viewpager.offscreenPageLimit = fragments.size
            binding.appBarMain.viewpager.currentItem = 0
        })

        vm.posCategory.observe(this, {
            binding.appBarMain.viewpager.setCurrentItem(it, false)
        })

        vm.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        })

        vm.responseApi.observe(this, {
            if (it.is_ok) {
                AppPreferences.auth = ""
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_menu) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
            return
        }
        super.onBackPressed()
    }

}