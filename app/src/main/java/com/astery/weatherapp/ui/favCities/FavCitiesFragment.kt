package com.astery.weatherapp.ui.favCities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.appComponent
import com.astery.weatherapp.databinding.FavCitiesFragmentBinding
import com.astery.weatherapp.model.City
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.searchCities.SearchCitiesFragmentDirections

class FavCitiesFragment:BaseFragment() {
    private val bind:FavCitiesFragmentBinding
    get() = _bind!! as FavCitiesFragmentBinding

    private val viewModel:FavCitiesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FavCitiesFragmentBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToWeather(City(2))
    }

    private fun moveToWeather(city: City) {
        findNavController().navigate(
            FavCitiesFragmentDirections.actionFavCitiesFragmentToWeatherTodayFragment(
                city
            )
        )
    }

}