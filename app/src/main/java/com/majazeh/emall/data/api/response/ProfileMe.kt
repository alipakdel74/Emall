package com.majazeh.emall.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileMe(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("nikname") val nikname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("username") val username: String,
    @SerializedName("avatar") val avatar: ImageUrl,
    @SerializedName("gender") val gender: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("address") val address: String?,
) : Parcelable