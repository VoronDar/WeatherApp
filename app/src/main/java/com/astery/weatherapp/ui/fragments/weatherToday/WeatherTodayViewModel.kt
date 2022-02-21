package com.astery.weatherapp.ui.fragments.weatherToday

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astery.weatherapp.model.pogo.*
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.storage.repository.Repository
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
                _weather.postValue(Error(Error.ResultError.PermissionDenied))
            }

            override fun onFailure() {
                _weather.postValue(Error(Error.ResultError.PermissionDenied))
            }
        })
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
        }
    }

    // load weather if has city, if not, get city and return to this func later
    private fun getWeatherData() {
        viewModelScope.launch(dispatcher) {

            if (w == null) {
                getGeolocation()
                return@launch
            }

            if (_weather.value is Idle) {
                _weather.postValue(Loading())

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


            }
        }
    }

    fun changeSelectedCityFav(favourite: Boolean) {
        viewModelScope.launch(dispatcher) {
            if (weather.value is Completed<WeatherData>) {
                val city = (weather.value as Completed<WeatherData>).result.city
                city.isFavourite = favourite
                changeCityFavouriteState(city)
            }
        }
    }

    fun getActualFavourite() {
        viewModelScope.launch(dispatcher) {
            if (weather.value is Completed) {
                val result = (weather.value as Completed<WeatherData>)
                val isCityFav = repository.isCityFavourite(result.result.city)
                _weather.postValue(
                    Completed(
                        WeatherData(
                            result.result.city.copy(isFavourite = isCityFav),
                            result.result.weatherData
                        ), result.isFromCache
                    )
                )
            }
        }
    }

    class Factory @Inject constructor(
        private val repository: Repository,
        private val dispatcher: CoroutineDispatcher
    ) {
        fun create(
            weather: WeatherData?,
            locationProvider: LocationProvider
        ): WeatherTodayViewModel =
            WeatherTodayViewModel(weather, locationProvider, repository, dispatcher)
    }
}

