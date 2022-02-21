package com.astery.weatherapp.storage.repository

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Result

interface Repository {
    suspend fun getCurrentWeather(city: City): Result<WeatherData>
    suspend fun getCity(location: Location): Result<City>
    suspend fun getLastViewedCity(): Result<City>
    suspend fun getCachedWeather(city: City): Result<WeatherData>
    suspend fun getFavouriteCities(): Result<List<WeatherData>>
    suspend fun getCities(searchQuery: String): Result<List<WeatherData>>
    suspend fun getCities(): Result<List<WeatherData>>
    suspend fun setLastViewedCity(city: City)

}
