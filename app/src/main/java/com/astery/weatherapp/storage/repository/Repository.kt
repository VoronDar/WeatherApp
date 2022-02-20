package com.astery.weatherapp.storage.repository

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.StateResult
import com.astery.weatherapp.ui.weatherToday.LocationProvider

interface Repository {
    suspend fun getCurrentWeather(city: City): StateResult<WeatherData>
    suspend fun getCity(location: LocationProvider.Location): StateResult<City>
}