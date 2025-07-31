package com.mariwronka.jankenpon.ui.common.extensions

import android.view.View

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
