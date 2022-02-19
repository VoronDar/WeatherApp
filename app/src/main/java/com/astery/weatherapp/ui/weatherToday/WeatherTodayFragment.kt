package com.astery.weatherapp.ui.weatherToday

import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.appComponent
import com.astery.weatherapp.databinding.WeatherFragmentBinding
import com.astery.weatherapp.model.City
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater
import com.astery.weatherapp.ui.utils.ArgumentsDelegate
import javax.inject.Inject

class WeatherTodayFragment : BaseFragment<WeatherFragmentBinding>() {
    private val city: City? by ArgumentsDelegate()

    private val viewModel: WeatherTodayViewModel by lazy {
        factory.create(city)
    }

    @Inject
    lateinit var factory: WeatherTodayViewModel.Factory


    override fun inflateBinding(): BindingInflater<WeatherFragmentBinding> {
        return WeatherFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
    }


    private fun moveToSearch() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToSearchCitiesFragment())
    }

    private fun moveToFavourite() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToFavCitiesFragment())
    }
}