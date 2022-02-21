package com.astery.weatherapp.ui.adapterUtils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


/**
 * abstraction for all adapters
 * */
abstract class BaseAdapter<R>(units: List<R>, diffUtil: DiffUtil.ItemCallback<R>) :
    ListAdapter<R, BaseViewHolder>(diffUtil) {
    open var blockListener: (Int) -> Unit = {}

    protected var units: List<R> = units
        set(value) {
        submitList(units)
        field = value
    }

    protected fun removeItem(position: Int){
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)

    }
}
