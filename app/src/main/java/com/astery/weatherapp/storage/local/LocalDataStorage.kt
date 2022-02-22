package com.astery.weatherapp.storage.local

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Result

interface LocalDataStorage {
    suspend fun getCurrentWeather(city: City): WeatherData?
    suspend fun addWeatherData(weather: WeatherData)
    suspend fun addCity(city: City)
    suspend fun getCity(lastId: String): Result<City>
    suspend fun getFavouriteCities(): Result<List<WeatherData>>

    suspend fun getCities(): Result<List<WeatherData>>
    suspend fun getCities(searchQuery: String): Result<List<WeatherData>>
    suspend fun changeCityFavouriteState(city: City)
    suspend fun isFavourite(city: City): Boolean?
}