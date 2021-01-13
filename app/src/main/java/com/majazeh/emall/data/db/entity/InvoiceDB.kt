package com.majazeh.emall.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.majazeh.emall.data.api.response.PreInvoice

@Entity(tableName = "tbl_invoice")
data class InvoiceDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "invoice_id") val invoice_id: String,
    @ColumnInfo(name = "product") val product: String,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "market_price") val market_price: Int,
    @ColumnInfo(name = "emall_price") val emall_price: Int,
    @ColumnInfo(name = "total_market_price") val total_market_price: Int,
    @ColumnInfo(name = "total_emall_price") val total_emall_price: Int,
    @ColumnInfo(name = "discount") val discount: Float,
) {
    companion object {
        fun to(invoice: PreInvoice): InvoiceDB {
            val product = Gson().toJson(invoice.product)
            return InvoiceDB(
                0,
                invoice.id,
                invoice.invoice_id,
                product,
                invoice.count,
                invoice.market_price,
                invoice.emall_price,
                invoice.total_market_price,
                invoice.total_emall_price,
                invoice.discount,
            )
        }
    }
}