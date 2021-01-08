package com.majazeh.emall.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.Product
import com.majazeh.emall.databinding.ItemProductBinding
import com.majazeh.emall.databinding.ItemShoppingCartBinding
import com.majazeh.emall.model.ProductType
import com.majazeh.emall.ui.activity.DetailActivity
import com.majazeh.emall.viewmodel.ShoppingCartViewModel

class ProductAdapter(
    private var models: MutableList<Product>,
    private val type: ProductType,
    private val vm: ShoppingCartViewModel? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (type == ProductType.PRODUCT)
            ProductHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            )
        else ShoppingCartHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_cart, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductHolder) {
            holder.binding.item = models[position]
            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                        DetailActivity::class.java
                    ).putExtra("data", models[position])
                )
            }
        }

        if (holder is ShoppingCartHolder) {
            holder.binding.item = models[position]
            holder.binding.vm = vm

            if (position % 2 == 1)
                holder.binding.cardShopping.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.white
                    )
                )
            else
                holder.binding.cardShopping.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.backgroundLightColor
                    )
                )
//            holder.itemView.setOnClickListener {
//                holder.itemView.context.startActivity(
//                    Intent(
//                        holder.itemView.context,
//                        DetailActivity::class.java
//                    ).putExtra("data", models[position])
//                )
//            }
        }
    }

    override fun getItemCount(): Int = models.size

    fun addAll(t: MutableList<Product>) {
        this.models.addAll(t)
        notifyItemRangeInserted(models.size, models.size - 1)
    }

    fun refresh(models: MutableList<Product>) {
        this.models.clear()
        this.models.addAll(models)
        notifyDataSetChanged()
    }

    private class ProductHolder(view: View) : BindingViewHolder<ItemProductBinding>(view)
    private class ShoppingCartHolder(view: View) : BindingViewHolder<ItemShoppingCartBinding>(view)
}