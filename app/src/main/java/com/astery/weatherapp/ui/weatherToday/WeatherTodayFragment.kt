package com.astery.weatherapp.ui.weatherToday

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.appComponent
import com.astery.weatherapp.databinding.SearchCitiesFragmentBinding
import com.astery.weatherapp.model.City
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.utils.ArgumentsDelegate
import timber.log.Timber
import javax.inject.Inject

class WeatherTodayFragment : BaseFragment() {
    private val bind: SearchCitiesFragmentBinding
        get() = _bind!! as SearchCitiesFragmentBinding

    private val city: City? by ArgumentsDelegate()

    private val viewModel: WeatherTodayViewModel by lazy {
        factory.create(city)
    }

    @Inject
    lateinit var factory: WeatherTodayViewModel.Factory

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = SearchCitiesFragmentBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun moveToSearch() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToSearchCitiesFragment())
    }

    private fun moveToFavourite() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToFavCitiesFragment())
    }
}