package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.pogo.WeatherEntity
import com.astery.weatherapp.model.state.StateResult

interface RemoteDataStorage {
    suspend fun getCurrentWeather(city: City): StateResult<WeatherData>
    suspend fun getCity(location: Location): StateResult<City>
}