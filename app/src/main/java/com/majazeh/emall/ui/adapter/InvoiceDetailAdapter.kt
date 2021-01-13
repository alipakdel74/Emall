package com.majazeh.emall.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.ali74.libkot.extension.CustomDataBinding.loadImage
import com.majazeh.emall.R
import com.majazeh.emall.data.api.response.PreInvoice

class InvoiceDetailAdapter(private val models: MutableList<PreInvoice>) : BaseAdapter() {
    override fun getCount(): Int = models.size

    override fun getItem(position: Int): Any = 0

    override fun getItemId(position: Int): Long = 0

    @Suppress("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.item_invoice_detail, parent, false)
        val ivShopping: AppCompatImageView = view.findViewById(R.id.ivShopping)
        val txtShoppingCart: AppCompatTextView = view.findViewById(R.id.txtShoppingCart)
        val txtAmount: AppCompatTextView = view.findViewById(R.id.txtAmount)
        val txtDiscount: AppCompatTextView = view.findViewById(R.id.txtDiscount)
        val txtTotal: AppCompatTextView = view.findViewById(R.id.txtTotal)

        ivShopping.loadImage(models[position].product.imageUrl.original)
        txtShoppingCart.text = models[position].product.title
        txtAmount.text = "تعداد : ${models[position].count}"
        txtDiscount.text = "تخفیف : ${models[position].discount.toInt()} "
        txtTotal.text = "مبلغ اصلی بازار : ${models[position].total_market_price} "

        return view
    }
}