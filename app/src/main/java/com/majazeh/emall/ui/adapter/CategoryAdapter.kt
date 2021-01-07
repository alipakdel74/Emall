package com.majazeh.emall.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.Category
import com.majazeh.emall.databinding.ItemCategoryBinding
import com.majazeh.emall.viewmodel.MainViewModel

class CategoryAdapter(
    private val models: MutableList<Category>, private val vm: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var positionSelected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CategoryHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryHolder) {
            holder.binding.item = models[position]
            holder.binding.vm = vm

            if (positionSelected == position)
                holder.binding.titleCategory.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.primaryColor
                    )
                )
            else
                holder.binding.titleCategory.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.black
                    )
                )

            holder.binding.titleCategory.setOnClickListener {
                positionSelected = position
                vm.subCategory(positionSelected)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = models.size

    class CategoryHolder(view: View) : BindingViewHolder<ItemCategoryBinding>(view)
}