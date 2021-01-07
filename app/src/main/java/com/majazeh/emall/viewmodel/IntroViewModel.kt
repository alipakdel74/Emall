package com.majazeh.emall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ali74.libkot.core.BaseViewModel
import com.majazeh.emall.R
import com.majazeh.emall.model.IntroData

class IntroViewModel : BaseViewModel() {
    private val _intros = MutableLiveData<MutableList<IntroData>>()
    val intro: LiveData<MutableList<IntroData>> = _intros

    init {
        _intros.value = mutableListOf(
            IntroData(
                "اشتري و حصّل خصومات \n" +
                        "خيالية مع تطبيق ايمول\n" +
                        "التطبيق الأول من نوعه في العراق", R.drawable.ic_percent, "#9084FF"
            ),
            IntroData(
                "تطبيق ايمول\n" +
                        "اكبر متجر الكتروني من حيث تعدد \n" +
                        "منتجاته بجوده عاليه و اسعار مناسبة", R.drawable.ic_mobile, "#00926B"
            ),
            IntroData(
                "جرّب و اشتري\n" +
                        "و راح نوصلك خلال دقايق", R.drawable.ic_truck, "#E55656"
            ),
        )
    }

}