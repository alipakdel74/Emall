package com.majazeh.emall.data.api.response

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("is_ok") val isOk: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("message_text") val messageText: String,
    @SerializedName("data") val data: Token,
)

data class Token(@SerializedName("token") val token: String)
