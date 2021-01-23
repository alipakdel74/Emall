package com.majazeh.emall.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.ali74.libkot.extension.CustomDataBinding.loadImage
import com.majazeh.emall.R

class ProfileAdapter(private val models: MutableList<Pair<String, Int>>) : BaseAdapter() {
    override fun getCount(): Int = models.size

    override fun getItem(position: Int): Any = 0

    override fun getItemId(position: Int): Long = 0

    @Suppress("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_profile, parent, false)
        val ivImage: AppCompatImageView = view.findViewById(R.id.ivImage)
        val txtText: AppCompatTextView = view.findViewById(R.id.txtText)
        ivImage.loadImage(models[position].second)
        txtText.text = models[position].first
        return view
    }
}