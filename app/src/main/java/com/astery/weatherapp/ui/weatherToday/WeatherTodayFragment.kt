package com.astery.weatherapp.ui.weatherToday

import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.WeatherFragmentBinding
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater
import com.astery.weatherapp.ui.utils.ArgumentsDelegate
import com.google.android.material.transition.MaterialSharedAxis
import timber.log.Timber
import javax.inject.Inject


class WeatherTodayFragment : BaseFragment<WeatherFragmentBinding>() {

    private val locationProvider = LocationProvider(this)
    private val weather: WeatherData? by ArgumentsDelegate()
    private val viewModel: WeatherTodayViewModel by lazy {
        factory.create(weather, locationProvider)
    }

    @Inject
    lateinit var factory: WeatherTodayViewModel.Factory

    override fun inflateBinding(): BindingInflater<WeatherFragmentBinding> {
        return WeatherFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    // MARK: listeners
    override fun setViewModelListeners() {
        viewModel.weather.observe(viewLifecycleOwner, WeatherObserver())
    }

    override fun prepareUI() {
        bind.favButton.setOnClickListener { moveToFavourite() }
        bind.searchButton.setOnClickListener { moveToSearch() }
        bind.geoButton.setOnClickListener { TODO() }
    }


    // MARK: render
    private inner class WeatherObserver : Observer<Result<WeatherData>> {
        override fun onChanged(result: Result<WeatherData>?) {
            Timber.d("${result!!::class.simpleName}")
            when (result) {
                is Idle -> renderLoading()
                is Loading -> renderLoading()
                is Completed<*> -> renderComplete(
                    WeatherDataForUI(
                        requireContext(),
                        (result.result as WeatherData).weatherData!!,
                        result.result.city
                    )
                )
                is Error -> renderError(result.error)
                else -> throw IllegalStateException("got invalid result state ${result::class.simpleName}")
            }
        }

        private fun renderLoading() {
            renderChangeVisibility(true)

        }

        private fun renderComplete(weather: WeatherDataForUI) {
            renderChangeVisibility(false)
            bind.run {
                city.text = weather.cityName
                temperature.text = weather.temperature
                feelTemperature.text = weather.feelsLike
                pressure.text = weather.pressure
                humidity.text = weather.humidity
                windSpeed.text = weather.windSpeed
                // TODO(favIcon = )
                time.text = weather.timestamp
                weatherState.setImageDrawable(weather.weatherIcon)
                weatherState.contentDescription = weather.weatherState
                weatherStateBackground.setImageDrawable(weather.weathericonBackground)

            }
        }

        private fun renderError(t: Error.ResultError) {
            Timber.d("got error ${t.name}")
            when (t) {
                Error.ResultError.PermissionDenied -> moveToSearch()
                else -> {
                }
            }
        }

        private fun renderChangeVisibility(isGone: Boolean) {
            if (bind.city.isGone == isGone) return

            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            TransitionManager.beginDelayedTransition(bind.workPanel, sharedAxis)
            TransitionManager.beginDelayedTransition(bind.root, sharedAxis)
            bind.weatherGroup.isGone = isGone
            bind.weatherState.isGone = isGone
            bind.weatherStateBackground.isGone = isGone

        }
    }

    // I clear context based variable in location provider because I give localProvider to viewModel
    override fun onDetach() {
        super.onDetach()
        locationProvider.clearFragment()
    }

    // MARK: navigation
    private fun moveToSearch() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToSearchCitiesFragment())
    }

    private fun moveToFavourite() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToFavCitiesFragment())
    }

}