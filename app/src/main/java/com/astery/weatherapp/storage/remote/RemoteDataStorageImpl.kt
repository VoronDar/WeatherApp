package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.storage.remote.retrofit.cities.CitiesRetrofitInstance
import com.astery.weatherapp.storage.remote.retrofit.weather.WeatherRetrofitInstance
import javax.inject.Inject

class RemoteDataStorageImpl @Inject constructor(
    private val weatherRetrofit: WeatherRetrofitInstance,
    private val citiesRetrofitInstance: CitiesRetrofitInstance
) : RemoteDataStorage