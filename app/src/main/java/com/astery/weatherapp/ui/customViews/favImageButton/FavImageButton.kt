package com.astery.weatherapp.ui.customViews.favImageButton

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.astery.weatherapp.R
import com.astery.weatherapp.ui.utils.drawable


class FavImageButton(context: Context, attrs: AttributeSet?) :
    AppCompatImageButton(context, attrs, R.style.icon) {
    private var isFavourite = false
        set(value) {
            field = value
            render()
        }


    fun init(isFavourite: Boolean, onStateChangedListener: (Boolean) -> Unit) {
        this.isFavourite = isFavourite
        setOnClickListener {
            this.isFavourite = !this.isFavourite
            onStateChangedListener(this.isFavourite) }
    }

    private fun render() {
        if (isFavourite)
            setImageDrawable(context.drawable(R.drawable.ic_baseline_star_24))
        else
            setImageDrawable(context.drawable(R.drawable.ic_baseline_star_border_24))
    }
}