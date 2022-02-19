package com.astery.weatherapp.ui.favCities

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.appComponent
import com.astery.weatherapp.databinding.FavCitiesFragmentBinding
import com.astery.weatherapp.model.City
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater

class FavCitiesFragment : BaseFragment<FavCitiesFragmentBinding>() {
    private val viewModel: FavCitiesViewModel by viewModels()
    override fun inflateBinding(): BindingInflater<FavCitiesFragmentBinding> {
        return FavCitiesFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
        TODO("Not yet implemented")
    }

    private fun moveToWeather(city: City) {
        findNavController().navigate(
            FavCitiesFragmentDirections.actionFavCitiesFragmentToWeatherTodayFragment(
                city
            )
        )
    }

}