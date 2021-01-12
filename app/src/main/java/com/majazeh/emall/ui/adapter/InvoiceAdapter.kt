package com.majazeh.emall.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.Invoice
import com.majazeh.emall.databinding.ItemInvoiceBinding
import com.majazeh.emall.viewmodel.InvoiceViewModel

class InvoiceAdapter(private val models: MutableList<Invoice>, private val vm: InvoiceViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        InvoiceHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_invoice, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InvoiceHolder) {
            holder.binding.item = models[position]
            holder.binding.vm = vm
            holder.binding.position = position

            if (position % 2 == 0)
                holder.binding.constrainDescInvoice.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.backgroundLightColor
                    )
                )
            else
                holder.binding.constrainDescInvoice.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.primaryLightColor
                    )
                )

            holder.itemView.setOnClickListener {
                vm.getInvoiceDetail(models[position].id)
            }
        }
    }

    override fun getItemCount(): Int = models.size

    fun addAll(t: MutableList<Invoice>) {
        this.models.addAll(t)
        notifyItemRangeInserted(models.size, models.size - 1)
    }

    private class InvoiceHolder(view: View) : BindingViewHolder<ItemInvoiceBinding>(view)
}