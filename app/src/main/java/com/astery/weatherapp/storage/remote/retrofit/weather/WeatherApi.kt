package com.astery.weatherapp.storage.remote.retrofit.weather

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Header("x-rapidapi-host") host: String, @Header("x-rapidapi-key") key: String,
        @Query("q") city: String, @Query("lang") lang: String, @Query("units") units: String
    ): WeatherApiEntity
}

class WeatherApiEntity{

}
