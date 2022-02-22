package com.astery.weatherapp.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


/**
 * abstraction for all adapters
 * */
abstract class BaseAdapter<R>(units: List<R>, diffUtil: DiffUtil.ItemCallback<R>) :
    ListAdapter<R, BaseViewHolder>(diffUtil) {
    open var blockListener: (Int) -> Unit = {}

    init{
        submitList(units)
    }

    // make submitList final to escape "accessing non-final property in constructor"
    final override fun submitList(list: List<R>?) = super.submitList(list)

    protected fun removeItem(position: Int){
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)

    }
}
