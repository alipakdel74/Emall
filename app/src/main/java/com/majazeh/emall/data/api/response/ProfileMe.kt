package com.majazeh.emall.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileMe(
    @SerializedName("id") val id: String,
    @SerializedName("name") var name: String?,
    @SerializedName("nikname") var nikname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("username") val username: String,
    @SerializedName("avatar") val avatar: ImageUrl,
    @SerializedName("gender") val gender: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("address") var address: String?,
) : Parcelable