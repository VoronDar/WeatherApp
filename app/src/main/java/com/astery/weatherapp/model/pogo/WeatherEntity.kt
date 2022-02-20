package com.astery.weatherapp.model.pogo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.astery.weatherapp.storage.local.db.converters.DateConverter
import com.astery.weatherapp.storage.local.db.converters.WeatherStateConverter
import java.util.*

@Entity
@TypeConverters(WeatherStateConverter::class, DateConverter::class)
data class WeatherEntity(
    // id weatherEntity = cityId
    @PrimaryKey val id: String,
    val temperature: Double,
    val feelLike: Double,
    val humidity: Int,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val state: WeatherState,
    val date: Date
)

data class WeatherData(val city: City, val weatherData: WeatherEntity)

enum class WeatherState {
    Sunny,
    Cloudy,
    Snow,
    Rain
}