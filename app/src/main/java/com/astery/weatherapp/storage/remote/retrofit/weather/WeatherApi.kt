package com.astery.weatherapp.storage.remote.retrofit.weather

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("currentconditions/v1/{key}")
    suspend fun getWeather(
        @Path("key") key: String,
        @Query("apikey") api: String,
        @Query("details") details: Boolean,
        @Query("language") language: String
    ): List<WeatherApiEntity>
}