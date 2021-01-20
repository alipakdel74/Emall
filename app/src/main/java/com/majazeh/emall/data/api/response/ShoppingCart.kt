package com.majazeh.emall.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoppingCart(
    @SerializedName("id") val id: String,
    @SerializedName("market_price") val market_price: Int,
    @SerializedName("emall_price") val emall_price: Int,
    @SerializedName("discount") val discount: Int,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: String?,
    @SerializedName("products") val products: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("details") val details: MutableList<PreInvoice>,
) : Parcelable

@Parcelize
data class PreInvoice(
    @SerializedName("id") val id: String,
    @SerializedName("invoice_id") val invoice_id: String,
    @SerializedName("product") var product: Product,
    @SerializedName("count") var count: Int,
    @SerializedName("market_price") val market_price: Int,
    @SerializedName("emall_price") val emall_price: Int,
    @SerializedName("total_market_price") val total_market_price: Int,
    @SerializedName("total_emall_price") val total_emall_price: Int,
    @SerializedName("discount") val discount: Float,
) : Parcelable