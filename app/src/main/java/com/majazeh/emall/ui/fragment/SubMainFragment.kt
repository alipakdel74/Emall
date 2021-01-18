package com.majazeh.emall.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingFragment
import com.ali74.libkot.patternBuilder.SnackBarBuilder
import com.ali74.libkot.recyclerview.EndlessRVScroll
import com.majazeh.emall.R
import com.majazeh.emall.databinding.SubMainBinding
import com.majazeh.emall.ui.adapter.ProductAdapter
import com.majazeh.emall.viewmodel.MainViewModel
import com.ogaclejapan.smarttablayout.utils.v4.Bundler
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubMainFragment : BindingFragment<SubMainBinding>() {

    private val vm by viewModel<MainViewModel>()

    private var adapter: ProductAdapter? = null
    private var idCategory = 0
    private var pageaction = 1

    override fun getLayoutResId(): Int = R.layout.fragment_sub_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            idCategory = getInt(CAT_ID)
        }

        vm.getProduct(1, "", idCategory, "")

        vm.productsData.observe(this, {
            if (pageaction == 1) {
                if (!it.isNullOrEmpty()) {
                    adapter = ProductAdapter(it, vm)
                    binding.rclProduct.adapter = adapter
                    binding.txtNull.visibility = View.GONE
                } else binding.txtNull.visibility = View.VISIBLE
            } else {
                adapter?.addAll(it)
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = GridLayoutManager(requireContext(), 2)
        binding.rclProduct.layoutManager = manager

        binding.rclProduct.addOnScrollListener(object : EndlessRVScroll(manager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.prbLazyLoad.visibility = View.VISIBLE
                pageaction = page + 1
                vm.getProduct(pageaction, "", idCategory, "")
            }
        })

        vm.isLoading.observe(this, {
            if (it)
                binding.prbLazyLoad.visibility = View.VISIBLE
            else
                binding.prbLazyLoad.visibility = View.INVISIBLE
        })
        vm.message.observe(this, {
            SnackBarBuilder(it).show(requireActivity())
        })
    }

    companion object {

        private const val CAT_ID = "CAT_ID"

        @JvmStatic
        fun arguments(idCategory: Int): Bundle {
            SubMainFragment()
            return Bundler().putInt(CAT_ID, idCategory).get()
        }
    }

}