package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class Explode(
    @SerializedName("is_ok") val is_ok: Boolean,
    @SerializedName("message_text") val message_text: String,
    @SerializedName("me") var me: ProfileMe?,
    @SerializedName("cart") val cart: ShoppingCart?,
    @SerializedName("products") val products: MutableList<Product>,
    @SerializedName("brands") val brands: MutableList<Brand>,
    @SerializedName("categories") val categories: MutableList<Category>,
    @SerializedName("version") val version: Version,
)

data class Version(
    @SerializedName("login") val login: Boolean,
    @SerializedName("api") val api: VersionValue,
    @SerializedName("android") val android: VersionValue,
    @SerializedName("ios") val ios: VersionValue,
)

data class VersionValue(
    @SerializedName("version") val version: String,
    @SerializedName("min") val min: String,
    @SerializedName("force") val force: Boolean,
    @SerializedName("link") val link: String,
)