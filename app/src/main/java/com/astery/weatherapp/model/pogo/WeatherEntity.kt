package com.astery.weatherapp.model.pogo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    // id weatherEntity = cityId
    @PrimaryKey val id: Int,
    val temperature: Double,
    val feelLike: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val isFavourite:Boolean,
    val state:WeatherState
)


data class WeatherData(val city:City, val weatherData: WeatherEntity){
}

enum class WeatherState{
    Sunny,
    Cloudy,
    Snow,
    Rain
}