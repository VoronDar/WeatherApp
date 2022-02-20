package com.astery.weatherapp.storage.remote.retrofit.weather

import com.astery.weatherapp.model.pogo.WeatherEntity
import com.astery.weatherapp.model.pogo.WeatherState
import com.google.gson.annotations.SerializedName
import java.util.*


data class WeatherApiEntity(
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("RealFeelTemperature") val feelsLike: Temperature,
    @SerializedName("EpochTime") val time: Long,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("RelativeHumidity") val humidity: Int,
    @SerializedName("Pressure") val pressure: Pressure,
    @SerializedName("Wind") val wind: Wind
){
    fun toWeather(id:String):WeatherEntity{
        return WeatherEntity(id, temperature.metric.value, feelsLike.metric.value, humidity, pressure.metric.value, wind.speed.metric.value, wind.direction.degrees,
        getWeatherState(), Date(time))
    }

    private fun getWeatherState():WeatherState{
        return when (weatherIcon){
            in (1..5) -> WeatherState.Sunny
            in (33..34) -> WeatherState.Sunny

            in (6..11) -> WeatherState.Cloudy
            in (19..21) -> WeatherState.Cloudy
            in (35..38) -> WeatherState.Cloudy
            43 -> WeatherState.Cloudy

            in (12..18) -> WeatherState.Rain
            in (39..42) -> WeatherState.Rain

            in (22..29) -> WeatherState.Snow
            44 -> WeatherState.Snow

            else -> WeatherState.Cloudy
        }
    }
}

data class Temperature(@SerializedName("Metric") val metric: Metric)

data class Metric(@SerializedName("Value") val value: Double)
data class Wind(
    @SerializedName("Direction") val direction: Direction,
    @SerializedName("Speed") val speed: Speed
)// "Unit": "km/h",

data class Direction(@SerializedName("Degrees") val degrees: Int)
data class Speed(@SerializedName("Metric") val metric: Metric)
data class Pressure(@SerializedName("Metric") val metric: Metric) // unit = mb
