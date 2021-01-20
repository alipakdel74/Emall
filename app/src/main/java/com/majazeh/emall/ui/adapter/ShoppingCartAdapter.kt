package com.majazeh.emall.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.databinding.ItemShoppingCartBinding
import com.majazeh.emall.ui.activity.DetailActivity
import com.majazeh.emall.viewmodel.ShoppingCartViewModel

class ShoppingCartAdapter(
    private val activity: Activity,
    private var models: MutableList<PreInvoice>,
    private val vm: ShoppingCartViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ShoppingCartHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping_cart, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
                        R.color.backgroundMediumColor
                    )
                )
            holder.itemView.setOnClickListener {
                activity.startActivityForResult(
                    Intent(
                        holder.itemView.context,
                        DetailActivity::class.java
                    ).putExtra("data", models[position].product), 0
                )
            }
        }

    }

    override fun getItemCount(): Int = models.size

    fun refresh(models: MutableList<PreInvoice>) {
        this.models.clear()
        this.models.addAll(models)
        notifyDataSetChanged()
    }

    private class ShoppingCartHolder(view: View) : BindingViewHolder<ItemShoppingCartBinding>(view)

}