package com.astery.weatherapp.ui.fragments.favCities

import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.FavCitiesFragmentBinding
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.ui.base.BaseFragment
import com.astery.weatherapp.ui.base.BindingInflater
import com.astery.weatherapp.ui.fragments.citiesList.CitiesAdapter
import com.astery.weatherapp.ui.fragments.citiesList.CitiesObserver
import javax.inject.Inject

class FavCitiesFragment : BaseFragment<FavCitiesFragmentBinding>() {
    @Inject
    lateinit var viewModelFactory: FavCitiesViewModel.Factory
    private val viewModel: FavCitiesViewModel by lazy(LazyThreadSafetyMode.NONE) {
        viewModelFactory.create()
    }
    private val adapter: CitiesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CitiesAdapter(listOf(), this::moveToWeather, viewModel::changeCityFavouriteState)
    }


    override fun inflateBinding(): BindingInflater<FavCitiesFragmentBinding> {
        return FavCitiesFragmentBinding::inflate
    }

    override fun prepareUI() {
        bind.back.setOnClickListener { activity?.onBackPressed() }
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
        viewModel.cities.observe(
            viewLifecycleOwner, CitiesObserver(
                bind.recyclerView, adapter, bind.loadStateView
            )
        )
    }


    private fun moveToWeather(data: WeatherData) {
        findNavController().navigate(
            FavCitiesFragmentDirections.actionFavCitiesFragmentToWeatherTodayFragment(
                data
            )
        )

    }
}