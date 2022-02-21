package com.astery.weatherapp.storage.remote.retrofit.cities

import com.astery.weatherapp.model.pogo.City
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CitiesApi {
    @GET("locations/v1/cities/geoposition/search")
    suspend fun getCityByLocation(
        @Query("apikey") apiKey: String, @Query("language") language: String,
        @Query("toplevel") topInfo: Boolean,
        @Query("q") latitude: String,
    ): City

    @GET("locations/v1/topcities/{topCities}")
    suspend fun getTopCities(
        @Path("topCities") topCities: Int,
        @Query("apikey") apiKey: String, @Query("language") language: String
    ): List<City>

    @GET("locations/v1/cities/search")
    suspend fun citySearch(
        @Query("apikey") apiKey: String, @Query("language") language: String,
        @Query("q") query: String
    ): List<City>
}
