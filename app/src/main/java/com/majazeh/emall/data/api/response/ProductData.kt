package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("data") val data: MutableList<Product>,
    @SerializedName("is_ok") val is_ok: Boolean,
    @SerializedName("message_text") val message_text: String,
    @SerializedName("links") val links: ProductLinks,
    @SerializedName("meta") val meta: ProductMeta,
)

data class ProductLinks(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String,
    @SerializedName("prev") val prev: String,
    @SerializedName("next") val next: String,
)

data class ProductMeta(
    @SerializedName("current_page") val current_page: Int,
    @SerializedName("from") val from: Int,
    @SerializedName("last_page") val last_page: Int,
    @SerializedName("path") val path: String,
    @SerializedName("per_page") val per_page: Int,
    @SerializedName("to") val to: Int,
    @SerializedName("total") val total: Int,
)
