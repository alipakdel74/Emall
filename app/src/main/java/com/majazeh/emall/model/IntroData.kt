package com.majazeh.emall.model

import androidx.annotation.DrawableRes

data class IntroData(
    val title: String,
    @DrawableRes val imageUrl: Int,
    val color: String,
)
