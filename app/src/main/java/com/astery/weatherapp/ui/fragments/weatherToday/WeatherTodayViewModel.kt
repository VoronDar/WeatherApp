package com.astery.weatherapp.ui.fragments.weatherToday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astery.weatherapp.model.pogo.*
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import com.astery.weatherapp.ui.fragments.baseChangeFav.ChangeFavViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherTodayViewModel constructor(
    private var w: WeatherData?,
    private val locationProvider: LocationProvider,
    repository: Repository,
    dispatcher: CoroutineDispatcher
) :
    ChangeFavViewModel(repository, dispatcher) {

    private val _weather: MutableLiveData<Result<WeatherData>> =
        MutableLiveData(Idle())
    val weather: LiveData<Result<WeatherData>>
        get() = _weather

    init {
        getWeatherData()
    }

    private fun getGeolocation() {
        locationProvider.getLocation(object : LocationProvider.LocationCallback {
            override fun onSuccess(location: Location) {
                getCity(location)
            }

            override fun onPermissionDenied() {
                Timber.d("permission denied")
                getLastViewedCity()
            }
        })
    }

    /** used when geo is not enabled */
    private fun getLastViewedCity(){
        viewModelScope.launch{
            getWeather(repository.getLastViewedCity())
        }
    }

    /** ask for city and than for weather */
    private fun getCity(location: Location) {
        viewModelScope.launch(dispatcher) {
            val cityResult = repository.getCity(location)
            if (cityResult is Completed) {
                w = WeatherData(
                    cityResult.result, w?.weatherData
                )
                getWeatherData()
            } else {
                _weather.postValue(Error(Error.ResultError.InvalidCity))
            }
    private fun getWeather(cityResult: Result<City>) {
        if (cityResult is Completed) {
            w = WeatherData(
                cityResult.result, w?.weatherData
            )
            getWeatherData()
        } else {
            _weather.value = Error(Error.ResultError.InvalidCity)
            asdasd()
        }
    }

    // load weather if has city, if not, get city and return to this func later

    private fun getWeatherData() {
            if (w == null) {
                getGeolocation()
                return
            }

        viewModelScope.launch(dispatcher) {

                async {
                    val value = repository.getCachedWeather(w!!.city)
                    if (_weather.value !is Completed && value is Completed) {
                        Timber.d("got weather from cache")
                        _weather.postValue(value)
                    }

                }
                async {
                    val value = repository.getCurrentWeather(w!!.city)
                    if (value is Completed || _weather.value !is Completed) {
                        Timber.d("tried to get actual")
                        _weather.postValue(value)
                    }
                }
            async {
                val value = repository.getCurrentWeather(w!!.city)

                // don't update if local returns data, but remote doesn't
                if (value is Completed || _weather.value !is Completed) {
                    Timber.d("tried to get actual weather")
                    _weather.value = value
                    asdasd()
                }

            }

        }
    }

    private fun asdasd(){
        // if there is no data and permission isn't granted ->
        // than we can't do anything and the only thing we can to do - is to move to another fragment
        if (_weather.value !is Completed && !LocationProvider.IS_PERMISSION_PROVIDED)
            _weather.value = Error(Error.ResultError.PermissionDenied)

    }


    class Factory @Inject constructor(private val repository: Repository) {
        fun create(
            weather: WeatherData?,
            locationProvider: LocationProvider
        ): WeatherTodayViewModel =
            WeatherTodayViewModel(weather, locationProvider, repository, dispatcher)
    }
}
