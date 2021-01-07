package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class ProfileMe(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("nikname") val nikname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("username") val username: String,
    @SerializedName("avatar") val avatar: ImageUrl,
    @SerializedName("gender") val gender: Any?,
    @SerializedName("location") val location: Any?,
    @SerializedName("address") val address: String?,
)