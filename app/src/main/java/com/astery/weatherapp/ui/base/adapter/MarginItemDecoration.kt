package com.astery.weatherapp.ui.base.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.astery.weatherapp.R

class MarginItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val margin = parent.context.resources.getDimension(R.dimen.margin).toInt()

            if (parent.getChildAdapterPosition(view) == 0) {
                top = margin
            }
            bottom = margin
            left = margin
            right = margin
        }
    }
}
