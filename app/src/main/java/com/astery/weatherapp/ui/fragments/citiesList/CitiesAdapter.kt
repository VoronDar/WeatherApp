package com.astery.weatherapp.ui.fragments.citiesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.astery.weatherapp.databinding.CityUnitBinding
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.ui.base.adapter.BaseAdapter
import com.astery.weatherapp.ui.base.adapter.BaseViewHolder
import com.bumptech.glide.Glide


class CitiesAdapter(
    units: List<WeatherData>,
    itemListener: (WeatherData) -> Unit,
    private val changeFavouriteListener: (city: City) -> Unit,
    private val isFavouriteList: Boolean = true
) :
    BaseAdapter<WeatherData>(units, diffUtil) {

    private lateinit var context: Context

    init {
        blockListener = { item -> itemListener(currentList[item]) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder {
        context = viewGroup.context
        return ViewHolder(viewGroup)
    }

    override fun onBindViewHolder(h: BaseViewHolder, position: Int) {
        val binding = h.binding as CityUnitBinding
        val unit = currentList[position]
        binding.run {
            name.text = unit.city.name
            favIcon.init(unit.city.isFavourite ?: false) { isFavourite ->
                unit.city.isFavourite = isFavourite
                changeFavouriteListener.invoke(unit.city)
                // if this is a favourite list - may remove
                if (!isFavourite && isFavouriteList)
                    removeItem(unit)
            }

            if (unit.weatherData != null) {
                val photoUrl =
                    "https://developer.accuweather.com/sites/default/files/${if (unit.weatherData!!.icon / 10 >= 1) "" else "0"}${unit.weatherData!!.icon}-s.png"
                Glide.with(context)
                    .load(photoUrl)
                    .into(binding.weatherIcon)
            }
        }

    }

    private fun removeItem(unit: WeatherData) {
        removeItem(currentList.indexOf(unit))
    }

    inner class ViewHolder(viewGroup: ViewGroup) :
        BaseViewHolder(
            blockListener, CityUnitBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup, false
            )
        )


}

private val diffUtil = object : DiffUtil.ItemCallback<WeatherData>() {
    override fun areItemsTheSame(
        oldItem: WeatherData,
        newItem: WeatherData
    ): Boolean {
        return oldItem.city.id == newItem.city.id
    }

    override fun areContentsTheSame(
        oldItem: WeatherData,
        newItem: WeatherData
    ): Boolean {
        return oldItem == newItem
    }
}
