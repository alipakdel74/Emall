package com.majazeh.emall.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: ProductValue,
    @SerializedName("brand") val brand: ProductValue,
    @SerializedName("market_price") val market_price: Int,
    @SerializedName("emall_price") val emall_price: Int,
    @SerializedName("discount") val discount: Float,
    @SerializedName("barcode") val barcode: String?,
    @SerializedName("unit_type") val unit_type: String,
    @SerializedName("unit") val unit: Float?,
    @SerializedName("status") val status: String,
    @SerializedName("quota") val quota: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image_url") val imageUrl: ImageUrl,
) : Parcelable

@Parcelize
data class ProductValue(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
): Parcelable

