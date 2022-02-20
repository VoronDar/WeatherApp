package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.Weather
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Result

interface RemoteDataStorage {
    suspend fun getCurrentWeather(city: City): Result<WeatherData>
    suspend fun getCity(location: Location): Result<City>
}