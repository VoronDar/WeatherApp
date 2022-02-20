package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.ResultError
import com.astery.weatherapp.model.state.StateError
import com.astery.weatherapp.model.state.StateResult
import com.astery.weatherapp.storage.remote.retrofit.cities.CitiesRetrofitInstance
import com.astery.weatherapp.storage.remote.retrofit.weather.WeatherRetrofitInstance
import javax.inject.Inject

class RemoteDataStorageImpl @Inject constructor(
    private val weatherRetrofit: WeatherRetrofitInstance,
    private val citiesRetrofitInstance: CitiesRetrofitInstance
) : RemoteDataStorage {
    override suspend fun getCurrentWeather(city: City): StateResult<WeatherData> {
        return StateError(ResultError.InvalidInternetConnection)
    }
}