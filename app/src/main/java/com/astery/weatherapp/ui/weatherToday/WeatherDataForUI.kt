package com.astery.weatherapp.ui.weatherToday

import android.content.Context
import com.astery.weatherapp.R
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.pogo.WeatherState
import com.astery.weatherapp.ui.utils.*
import java.util.*

data class WeatherDataForUI(val context: Context, private val weatherData: WeatherData) {
    val city = weatherData.city.name
    val temperature = context.getString(R.string.temperature, weatherData.weatherData.temperature)
    val feelsLike = context.getString(R.string.temperature, weatherData.weatherData.feelLike)
    val humidity = context.getString(R.string.humidity, weatherData.weatherData.humidity)
    val pressure = context.getString(R.string.pressure, weatherData.weatherData.humidity)
    val windSpeed = context.getString(R.string.windSpeed, weatherData.weatherData.windSpeed)
    val timestamp: String
        get() =
            Calendar.getInstance().run {
                time = Date(weatherData.weatherData.date.time)
                context.resources.getString(R.string.date, day(), month(), year(), hour(), minute())
            }
    val weatherIcon = context.drawable(
        when (weatherData.weatherData.state) {
            WeatherState.Sunny -> R.drawable.ic_sun
            WeatherState.Cloudy -> R.drawable.ic_cloudy
            WeatherState.Snow -> R.drawable.ic_snow
            WeatherState.Rain -> R.drawable.ic_rain
        }
    )
    val weathericonBackground = context.drawable(
        when (weatherData.weatherData.state) {
            WeatherState.Sunny -> R.drawable.sun_back
            WeatherState.Cloudy -> R.drawable.cloud_back
            WeatherState.Snow -> R.drawable.snow_back
            WeatherState.Rain -> R.drawable.rain_back
        }
    )
    val weatherState = context.resources.getString(
        when (weatherData.weatherData.state) {
            WeatherState.Sunny -> R.string.cd_weather_state_sun
            WeatherState.Cloudy -> R.string.cd_weather_state_cloud
            WeatherState.Snow -> R.string.cd_weather_state_snow
            WeatherState.Rain -> R.string.cd_weather_state_rain
        }
    )
}