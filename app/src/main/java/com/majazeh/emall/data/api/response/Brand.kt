package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_url") val image_url: ImageUrl,
    @SerializedName("count") val count: Int,
)
