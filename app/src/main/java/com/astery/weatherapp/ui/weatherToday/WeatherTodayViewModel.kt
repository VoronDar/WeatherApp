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
    private var city: City?,
    private val locationProvider: LocationProvider,
    private val repository: Repository
) :
    ViewModel() {

    private val _weather: MutableLiveData<StateResult<WeatherData>> = MutableLiveData(StateIdle())
    val weather: LiveData<StateResult<WeatherData>>
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
                _weather.value = StateError(ResultError.PermissionDenied)
            }

            override fun onFailure() {
                _weather.value = StateError(ResultError.PermissionDenied)
            }
        })
    }

    /** ask for city and than for weather */
    private fun getCity(location: Location) {
        viewModelScope.launch {
            val cityResult = repository.getCity(location)
            if (cityResult is StateCompleted) {
                city = cityResult.result
                getWeatherData()
            } else {
                _weather.value = StateError(ResultError.InvalidCity)
            }
        }
    }

    // load weather if has city, if not, get city and return to this func later
    private fun getWeatherData() {
        viewModelScope.launch {

            if (city == null) {
                getGeolocation()
                return@launch
            }

            if (_weather.value is StateIdle) {
                _weather.value = StateLoading()

                async{
                    val value = repository.getCachedWeather(city!!)
                    if (_weather.value !is StateCompleted && value is StateCompleted) {
                        Timber.d("got weather from cache")
                        _weather.value = value
                    }
                }

                async {
                    val value = repository.getCurrentWeather(city!!)
                    if (value is StateCompleted || _weather.value !is StateCompleted) {
                        Timber.d("tried to get actual")
                        _weather.value = value
                    }
                }


            }
        }
    }

    class Factory @Inject constructor(private val repository: Repository) {
        fun create(city: City?, locationProvider: LocationProvider): WeatherTodayViewModel =
            WeatherTodayViewModel(city, locationProvider, repository)
    }
}

