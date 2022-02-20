package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.StateResult

interface RemoteDataStorage {
    suspend fun getCurrentWeather(city: City): StateResult<WeatherData>
}