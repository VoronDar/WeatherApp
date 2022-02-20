package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.BuildConfig
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.ResultError
import com.astery.weatherapp.model.state.StateCompleted
import com.astery.weatherapp.model.state.StateError
import com.astery.weatherapp.model.state.StateResult
import com.astery.weatherapp.storage.remote.retrofit.cities.CitiesRetrofitInstance
import com.astery.weatherapp.storage.remote.retrofit.weather.WeatherRetrofitInstance
import timber.log.Timber
import javax.inject.Inject

class RemoteDataStorageImpl @Inject constructor(
    private val weatherRetrofit: WeatherRetrofitInstance,
    private val citiesRetrofit: CitiesRetrofitInstance
) : RemoteDataStorage {


    override suspend fun getCurrentWeather(city: City): StateResult<WeatherData> {
        return try {
            StateCompleted(WeatherData(city,
                weatherRetrofit.weatherApi.getWeather(city.id, key, true, lang).first()
                    .toWeather(city.id)
            ), false
            )
        } catch (e: Exception) {
            Timber.d("caught an exception ${e::class.simpleName} ${e.localizedMessage}")
            StateError(ResultError.UnexpectedBug)
        }
    }

    override suspend fun getCity(location: Location): StateResult<City> {
        return try {
            val it = citiesRetrofit.api.getCityByLocation(
                key,
                lang,
                true,
                "${location.latitude},${location.longitude}"
            )
            if (it.id == "null") StateError(ResultError.UnexpectedBug)
            else StateCompleted(it, false)
        } catch (e: Exception) {
            Timber.d("caught an exception ${e::class.simpleName} ${e.localizedMessage}")
            StateError(ResultError.UnexpectedBug)
        }
    }


    private val key = BuildConfig.WEATHER_API_KEY
    private val lang = "ru"
}