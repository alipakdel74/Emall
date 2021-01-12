package com.majazeh.emall.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.databinding.ItemProductBinding
import com.majazeh.emall.ui.activity.DetailActivity
import com.majazeh.emall.viewmodel.MainViewModel

class ProductAdapter(
    private var models: MutableList<Product>,
    private val vm: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ProductHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductHolder) {
            holder.binding.item = models[position]
            holder.binding.vm = vm
            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                        DetailActivity::class.java
                    ).putExtra("data", models[position])
                )
            }
        }
    }

    override fun getItemCount(): Int = models.size

    fun addAll(t: MutableList<Product>) {
        this.models.addAll(t)
        notifyItemRangeInserted(models.size, models.size - 1)
    }

    private class ProductHolder(view: View) : BindingViewHolder<ItemProductBinding>(view)
}