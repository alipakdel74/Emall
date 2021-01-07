package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class Logout(
    @SerializedName("message") val message: String,
    @SerializedName("is_ok") val is_ok: Boolean,
    @SerializedName("message_text") val message_text: String,
)
