package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class InvoiceData(
    @SerializedName("message") val message: String,
    @SerializedName("is_ok") val is_ok: Boolean,
    @SerializedName("message_text") val message_text: String,
    @SerializedName("data") val data: MutableList<Invoice>,
)

data class Invoice(
    @SerializedName("id") val id: String,
    @SerializedName("market_price") val market_price: Int,
    @SerializedName("emall_price") val emall_price: Int,
    @SerializedName("discount") val discount: Int?,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: String,
    @SerializedName("products") val products: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("location") val location: String,
    @SerializedName("address") val address: String?,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
)