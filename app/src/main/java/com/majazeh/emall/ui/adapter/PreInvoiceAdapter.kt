package com.majazeh.emall.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ali74.libkot.BindingViewHolder
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.PreInvoice
import com.majazeh.emall.databinding.ItemInvoicePreBinding
import com.majazeh.emall.ui.activity.DetailActivity
import com.majazeh.emall.viewmodel.PreInvoiceViewModel

class PreInvoiceAdapter(
    private val activity: Activity,
    private var models: MutableList<PreInvoice>,
    private val vm: PreInvoiceViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PreInvoiceHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_pre, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PreInvoiceHolder) {
            holder.binding.invoice = models[position]
            holder.binding.position = position
            holder.binding.vm = vm

            holder.binding.btnEdit.setOnClickListener {
                activity.startActivityForResult(
                    Intent(activity, DetailActivity::class.java)
                        .putExtra("preInvoice", models[position]),
                    0
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

    fun removeItem(model: PreInvoice) {
        this.models.remove(model)
        notifyDataSetChanged()
    }

    private class PreInvoiceHolder(view: View) : BindingViewHolder<ItemInvoicePreBinding>(view)

}