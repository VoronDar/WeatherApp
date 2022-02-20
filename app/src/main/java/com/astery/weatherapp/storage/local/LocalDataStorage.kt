package com.astery.weatherapp.storage.local

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.pogo.WeatherEntity

interface LocalDataStorage {
    suspend fun getCurrentWeather(city: City):WeatherData
    suspend fun addWeatherData(weather:WeatherData)
}