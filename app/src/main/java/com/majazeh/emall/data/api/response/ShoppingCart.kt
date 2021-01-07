package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class ShoppingCart(
    @SerializedName("id") val id: String,
    @SerializedName("market_price") val market_price: Int,
    @SerializedName("emall_price") val emall_price: Int,
    @SerializedName("discount") val discount: Float,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: String?,
    @SerializedName("products") val products: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("location") val location: Any?,
    @SerializedName("address") val address: String?,
    @SerializedName("details") val details: MutableList<Product>,
)