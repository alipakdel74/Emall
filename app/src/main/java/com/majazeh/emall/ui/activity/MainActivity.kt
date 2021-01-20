package com.majazeh.emall.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingActivity
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.ali74.libkot.recyclerview.EndlessRVScroll
import com.ali74.libkot.utils.hideKeyboard
import com.ali74.libkot.utils.showKeyboard
import com.majazeh.emall.R
import com.majazeh.emall.databinding.MainBinding
import com.majazeh.emall.ui.adapter.CategoryAdapter
import com.majazeh.emall.ui.adapter.ProductAdapter
import com.majazeh.emall.ui.dialog.QuestionDialog
import com.majazeh.emall.ui.fragment.MainFragment
import com.majazeh.emall.utils.AppPreferences
import com.majazeh.emall.utils.ViewPagerAdapter
import com.majazeh.emall.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BindingActivity<MainBinding>() {

    private val vm by viewModel<MainViewModel>()
    private var pageactionSearch = 1
    private var adapter: ProductAdapter? = null

    private val questionDialog by lazy { QuestionDialog(this) }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

        binding.appBarMain.toolbar.setNavigationOnClickListener {
            enterReveal(binding.appBarMain.constrainSearch)
        }

        searchProduct()

        questionDialog.sendData(object : QuestionDialog.SendData {
            override fun data(title: String, desc: String) {
                vm.request(title, desc)
            }
        })

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_user -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.nav_content -> {
                    startActivity(Intent(this, ContentActivity::class.java))
                }
                R.id.nav_intro -> {
                    startActivity(Intent(this, IntroActivity::class.java))
                }
                R.id.nav_invoice -> {
                    startActivity(Intent(this, InvoiceActivity::class.java))
                }
                R.id.nav_preInvoice -> {
                    startActivity(Intent(this, PreInvoiceActivity::class.java))
                }
                R.id.nav_question -> {
                    questionDialog.show()
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
                binding.navView.menu.findItem(R.id.nav_preInvoice).isVisible = true
                binding.navView.menu.findItem(R.id.nav_exit).isVisible = true
                binding.navView.menu.findItem(R.id.nav_profile).isVisible = true
            } else {
                binding.navView.menu.findItem(R.id.nav_user).isVisible = true
                binding.navView.menu.findItem(R.id.nav_invoice).isVisible = false
                binding.navView.menu.findItem(R.id.nav_preInvoice).isVisible = false
                binding.navView.menu.findItem(R.id.nav_exit).isVisible = false
                binding.navView.menu.findItem(R.id.nav_profile).isVisible = false
            }
        })
        vm.me.observe(this, {
            binding.navView.getHeaderView(0)
                .findViewById<AppCompatTextView>(R.id.fullName).text = ""
            binding.navView.getHeaderView(0)
                .findViewById<AppCompatTextView>(R.id.username).text = ""
            it?.apply {
                if (!name.isNullOrEmpty())
                    binding.navView.getHeaderView(0)
                        .findViewById<AppCompatTextView>(R.id.fullName).text = name
                binding.navView.getHeaderView(0)
                    .findViewById<AppCompatTextView>(R.id.username).text = mobile
            }
        })

        vm.request.observe(this, {
            if (it)
                questionDialog.dismiss()
        })

        vm.categories.observe(this, {
            binding.appBarMain.rclCategory.adapter = CategoryAdapter(it, vm)
            val fragments = mutableListOf<Fragment>()
            for (f in it)
                fragments.add(MainFragment().newInstance(f))

            binding.appBarMain.viewpager.adapter =
                ViewPagerAdapter(supportFragmentManager, fragments)
            binding.appBarMain.viewpager.offscreenPageLimit = 1
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

        vm.logout.observe(this, {
            if (it.is_ok) {
                AppPreferences.auth = ""
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(this)
        })

        vm.toast.observe(this, {
            SnackBarBuilder(getString(it)).show(this)
        })

    }

    private fun searchProduct() {
        binding.appBarMain.ivCloseSearch.setOnClickListener {
            if (binding.appBarMain.edtSearch.text.toString().trim().isNotEmpty()) {
                binding.appBarMain.edtSearch.setText("")
                return@setOnClickListener
            }
            exitReveal(binding.appBarMain.constrainSearch)
            binding.appBarMain.rclSearch.adapter = null
            pageactionSearch = 1
        }

        binding.appBarMain.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (EditorInfo.IME_ACTION_SEARCH == actionId)
                binding.appBarMain.ivSearch.callOnClick()
            return@setOnEditorActionListener true
        }

        binding.appBarMain.ivSearch.setOnClickListener {
            if (binding.appBarMain.edtSearch.text.toString().trim().isEmpty())
                return@setOnClickListener
            pageactionSearch = 1
            binding.appBarMain.rclSearch.adapter = null
            vm.getProductSearch(1, binding.appBarMain.edtSearch.text.toString().trim(), 0, "")
        }

        vm.productsDataSearch.observe(this, {
            hideKeyboard(this)
            if (pageactionSearch == 1) {

                if (it.data.isNullOrEmpty())
                    binding.appBarMain.txtNullSearch.visibility = View.VISIBLE
                else binding.appBarMain.txtNullSearch.visibility = View.GONE

                if (binding.appBarMain.rclSearch.adapter == null) {
                    adapter = ProductAdapter(it.data, vm)
                    binding.appBarMain.rclSearch.adapter = adapter
                }
            } else {
                adapter?.addAll(it.data)
            }
        })
        vm.lazyLoad.observe(this, {
            if (it)
                binding.appBarMain.prbLazyLoad.visibility = View.VISIBLE
            else
                binding.appBarMain.prbLazyLoad.visibility = View.INVISIBLE
        })

        val manager = GridLayoutManager(this, 2)
        binding.appBarMain.rclSearch.layoutManager = manager
        binding.appBarMain.rclSearch.addOnScrollListener(object : EndlessRVScroll(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.appBarMain.prbLazyLoad.visibility = View.VISIBLE
                pageactionSearch = page + 1
                vm.getProductSearch(
                    pageactionSearch,
                    binding.appBarMain.edtSearch.text.toString().trim(),
                    0,
                    ""
                )
            }
        })
    }


    private fun enterReveal(myView: View) {
        // get the center for the clipping circle
        val cx = myView.measuredWidth / 2
        val cy = myView.measuredHeight / 2
        // get the final radius for the clipping circle
        val finalRadius = myView.width.coerceAtLeast(myView.height) / 2
        // create the animator for this view (the start radius is zero)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim: Animator =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius.toFloat())
            myView.visibility = View.VISIBLE
            anim.start()
        } else {
            myView.visibility = View.VISIBLE
        }
        binding.appBarMain.edtSearch.requestFocus(800)
        binding.appBarMain.edtSearch.isFocusable = true
        binding.appBarMain.edtSearch.isFocusableInTouchMode = true
        showKeyboard(this)
    }

    private fun exitReveal(myView: View) {
        // get the center for the clipping circle
        val cx = myView.measuredWidth / 2
        val cy = myView.measuredHeight / 2
        // get the initial radius for the clipping circle
        val initialRadius = myView.width / 2
        // create the animation (the final radius is zero)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.GONE
                }
            })
            anim.start()
        } else {
            myView.visibility = View.GONE
        }
        binding.appBarMain.edtSearch.isFocusable = false
        binding.appBarMain.edtSearch.isFocusableInTouchMode = false
        binding.appBarMain.txtNullSearch.visibility = View.GONE
        binding.appBarMain.prbLazyLoad.visibility = View.GONE
        hideKeyboard(this)
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

    override fun onResume() {
        super.onResume()
        vm.getUser()
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is MainFragment)
            fragment.onclickAddToBasket(object : MainFragment.AddToBasket {
                override fun onclick(count: Int) {
                    binding.appBarMain.fab.text = count.toString()
                    runOnUiThread {
                        if (count == 0)
                            binding.appBarMain.fab.shrink()
                        else
                            binding.appBarMain.fab.extend()
                    }
                }
            })
    }

}