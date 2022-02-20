package com.astery.weatherapp.ui.weatherToday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.weatherapp.model.pogo.*
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherTodayViewModel constructor(
    private var w: WeatherData?,
    private val locationProvider: LocationProvider,
    private val repository: Repository
) :
    ViewModel() {

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
                _weather.value = Error(Error.ResultError.PermissionDenied)
            }

            override fun onFailure() {
                _weather.value = Error(Error.ResultError.PermissionDenied)
            }
        })
    }

    /** ask for city and than for weather */
    private fun getCity(location: Location) {
        viewModelScope.launch {
            val cityResult = repository.getCity(location)
            if (cityResult is Completed) {
                w = WeatherData(
                    cityResult.result, w?.weatherData
                )
                getWeatherData()
            } else {
                _weather.value = Error(Error.ResultError.InvalidCity)
            }
        }
    }

    // load weather if has city, if not, get city and return to this func later
    private fun getWeatherData() {
        viewModelScope.launch {

            if (w == null) {
                getGeolocation()
                return@launch
            }

            if (_weather.value is Idle) {
                _weather.value = Loading()

                async {
                    val value = repository.getCachedWeather(w!!.city)
                    if (_weather.value !is Completed && value is Completed) {
                        Timber.d("got weather from cache")
                        _weather.value = value
                    }
                }

                async {
                    val value = repository.getCurrentWeather(w!!.city)
                    if (value is Completed || _weather.value !is Completed) {
                        Timber.d("tried to get actual")
                        _weather.value = value
                    }
                }


            }
        }
    }

    class Factory @Inject constructor(private val repository: Repository) {
        fun create(
            weather: WeatherData?,
            locationProvider: LocationProvider
        ): WeatherTodayViewModel =
            WeatherTodayViewModel(weather, locationProvider, repository)
    }
}

