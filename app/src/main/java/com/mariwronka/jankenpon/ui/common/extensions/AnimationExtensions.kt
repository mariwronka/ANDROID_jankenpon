package com.mariwronka.jankenpon.ui.common.extensions

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.startResAnimation(@AnimRes animRes: Int) {
    val animation = AnimationUtils.loadAnimation(context, animRes)
    startAnimation(animation)
}
