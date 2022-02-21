package com.astery.weatherapp.ui.citiesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.astery.weatherapp.databinding.CityUnitBinding
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.ui.adapterUtils.BaseAdapter
import com.astery.weatherapp.ui.adapterUtils.BaseViewHolder
import com.bumptech.glide.Glide


class CitiesAdapter(
    units: List<WeatherData>,
    itemListener: (WeatherData) -> Unit
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
            if (unit.weatherData != null) {
                // TODO(переделать под двузначные)
                val photoUrl =
                    "https://developer.accucom/sites/default/files/0${unit.weatherData.icon}-s.png"
                Glide.with(context)
                    .load(photoUrl)
                    .into(binding.weatherIcon)
            }
        }

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