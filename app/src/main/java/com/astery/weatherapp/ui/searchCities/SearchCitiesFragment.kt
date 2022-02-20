package com.astery.weatherapp.ui.searchCities

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.SearchCitiesFragmentBinding
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater

class SearchCitiesFragment : BaseFragment<SearchCitiesFragmentBinding>() {
    private val viewModel: SearchCitiesViewModel by viewModels()
    override fun inflateBinding(): BindingInflater<SearchCitiesFragmentBinding> {
        return SearchCitiesFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
        TODO("Not yet implemented")
    }

    private fun moveToWeather(city: City) {
        findNavController().navigate(
            SearchCitiesFragmentDirections.actionSearchCitiesFragmentToWeatherTodayFragment(
                city
            )
        )
    }
}