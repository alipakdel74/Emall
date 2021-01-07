package com.majazeh.emall.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.databinding.ItemIntroBinding
import com.majazeh.emall.model.IntroData

class IntroPagerAdapter(private val models: MutableList<IntroData>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getItemPosition(`object`: Any): Int = POSITION_NONE

    override fun getPageTitle(position: Int): CharSequence = models[position].title

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getCount(): Int = models.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.item_intro, container, false)
        val item = IntroHolder(view)
        item.binding.item = models[position]
        container.addView(view)
        return view
    }

    private class IntroHolder(view: View) : BindingViewHolder<ItemIntroBinding>(view)

}