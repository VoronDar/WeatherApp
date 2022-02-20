package com.astery.weatherapp.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


fun Context.drawable(id:Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Context.color(id:Int): Int {
    return ContextCompat.getColor(this, id)
}
