package com.mariwronka.jankenpon.ui.common.extensions

import android.view.View

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.blockTouch() {
    isClickable = true
    isFocusable = true
}
