package com.astery.weatherapp.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import java.util.*

// MARK: resources
fun Context.drawable(id:Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Context.color(id:Int): Int {
    return ContextCompat.getColor(this, id)
}

// MARK: calendar

fun Calendar.year():Int{
    return get(Calendar.YEAR)
}
fun Calendar.month():Int{
    return get(Calendar.MONTH)
}
fun Calendar.day():Int{
    return get(Calendar.DAY_OF_MONTH)
}
fun Calendar.hour():Int{
    return get(Calendar.HOUR_OF_DAY)
}
fun Calendar.minute():Int{
    return get(Calendar.MINUTE)
}