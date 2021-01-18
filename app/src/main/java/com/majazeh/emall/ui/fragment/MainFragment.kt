package com.majazeh.emall.ui.fragment

import android.os.Bundle
import android.view.View
import com.ali74.libkot.BindingFragment
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.Category
import com.majazeh.emall.databinding.MainFragmentBinding
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainFragment : BindingFragment<MainFragmentBinding>() {

    private var category: Category? = null

    override fun getLayoutResId(): Int = R.layout.fragment_main

    fun newInstance(category: Category): MainFragment {
        val fragmentFirst = MainFragment()
        val args = Bundle()
        args.putParcelable("CATEGORY", category)
        fragmentFirst.arguments = args
        return fragmentFirst
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getParcelable("CATEGORY")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category?.subs?.apply {
            val fragmentPagerItems = FragmentPagerItems(requireContext())
            for (smartFragment in this) {
                fragmentPagerItems.add(
                    FragmentPagerItem.of(
                        smartFragment.title,
                        SubMainFragment::class.java,
                        SubMainFragment.arguments(smartFragment.id)
                    )
                )
            }
            binding.viewpager.adapter =
                FragmentPagerItemAdapter(childFragmentManager, fragmentPagerItems)
            binding.smartTab.setViewPager(binding.viewpager)
            binding.viewpager.offscreenPageLimit = 2
            binding.viewpager.currentItem = size - 1
        }
    }


}