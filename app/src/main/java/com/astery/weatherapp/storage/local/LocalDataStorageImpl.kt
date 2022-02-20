package com.astery.weatherapp.storage.local

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.storage.local.db.AppDatabase
import javax.inject.Inject

class LocalDataStorageImpl @Inject constructor(private val appDatabase: AppDatabase):LocalDataStorage {
    override suspend fun getCurrentWeather(city: City): WeatherData {
        return WeatherData(city, appDatabase.weatherDao().getWeather(city.id))
    }

    override suspend fun addWeatherData(weather: WeatherData) {
        appDatabase.weatherDao().addWeatherData(weather.weatherData)
        appDatabase.cityDao().addCity(weather.city)
    }
}