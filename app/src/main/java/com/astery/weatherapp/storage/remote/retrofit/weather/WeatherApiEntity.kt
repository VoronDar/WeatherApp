package com.astery.weatherapp.storage.remote.retrofit.weather

import com.astery.weatherapp.model.pogo.Weather
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
) {
    fun toWeather(id: String): Weather {
        return Weather(
            id,
            temperature.metric.value,
            feelsLike.metric.value,
            humidity,
            pressure.metric.value,
            wind.speed.metric.value,
            wind.direction.degrees,
            getWeatherState(),
            // date consumes milliseconds, server returns seconds
            Date(time*1000),
            weatherIcon
        )
    }

    private fun getWeatherState(): Weather.State {
        return when (weatherIcon) {
            in (1..5) -> Weather.State.Sunny
            in (33..34) -> Weather.State.Sunny

            in (6..11) -> Weather.State.Cloudy
            in (19..21) -> Weather.State.Cloudy
            in (35..38) -> Weather.State.Cloudy
            43 -> Weather.State.Cloudy

            in (12..18) -> Weather.State.Rain
            in (39..42) -> Weather.State.Rain

            in (22..29) -> Weather.State.Snow
            44 -> Weather.State.Snow

            else -> Weather.State.Cloudy
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

}
