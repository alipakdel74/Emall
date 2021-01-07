package com.majazeh.emall.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("category_detail") val category_detail: String? = null,
    @SerializedName("order") val order: Int,
    @SerializedName("status") val status: String,
    @SerializedName("level") val level: Int,
    @SerializedName("subs") val subs: MutableList<Category>? = null,
) : Parcelable