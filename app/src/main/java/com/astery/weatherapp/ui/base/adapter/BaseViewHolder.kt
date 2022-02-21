package com.astery.weatherapp.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * abstraction for all RecyclerView.viewHolders
 * */
abstract class BaseViewHolder(blockListener: (Int) -> Unit, val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            blockListener(absoluteAdapterPosition)
        }
    }
}