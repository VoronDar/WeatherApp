package com.astery.weatherapp.ui.favCities

import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.FavCitiesFragmentBinding
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater
import com.astery.weatherapp.ui.citiesList.CitiesAdapter
import com.astery.weatherapp.ui.citiesList.CitiesObserver
import javax.inject.Inject

class FavCitiesFragment : BaseFragment<FavCitiesFragmentBinding>() {
    @Inject
    lateinit var viewModelFactory: FavCitiesViewModel.Factory
    private val viewModel: FavCitiesViewModel by lazy {
        viewModelFactory.create()
    }
    private val adapter: CitiesAdapter = CitiesAdapter(listOf(), this::moveToWeather)


    override fun inflateBinding(): BindingInflater<FavCitiesFragmentBinding> {
        return FavCitiesFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
        viewModel.cities.observe(
            viewLifecycleOwner, CitiesObserver(
                bind.recyclerView, adapter, bind.loadStateView, ::reloadCities
            )
        )
    }

    private fun reloadCities() {

    }

    private fun moveToWeather(data: WeatherData) {
        findNavController().navigate(
            FavCitiesFragmentDirections.actionFavCitiesFragmentToWeatherTodayFragment(
                data
            )
        )

    }
}