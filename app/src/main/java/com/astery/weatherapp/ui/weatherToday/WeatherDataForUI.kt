package com.astery.weatherapp.ui.weatherToday

import android.content.Context
import com.astery.weatherapp.R
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Weather
import com.astery.weatherapp.ui.utils.*
import java.util.*

data class WeatherDataForUI(
    val context: Context,
    private val weather: Weather,
    private val city: City,
) {
    
    val cityName = city.name
    val temperature = context.getString(R.string.temperature, weather.temperature)
    val feelsLike = context.getString(R.string.temperature, weather.feelLike)
    val humidity = context.getString(R.string.humidity, weather.humidity)
    val pressure = context.getString(R.string.pressure, weather.pressure)
    val windSpeed = context.getString(R.string.windSpeed, weather.windSpeed)
    val timestamp: String
        get() =
            Calendar.getInstance().run {
                time = Date(weather.date.time)
                context.resources.getString(R.string.date, day(), month(), year(), hour(), minute())
            }
    val weatherIcon = context.drawable(
        when (weather.state) {
            Weather.State.Sunny -> R.drawable.ic_sun
            Weather.State.Cloudy -> R.drawable.ic_cloudy
            Weather.State.Snow -> R.drawable.ic_snow
            Weather.State.Rain -> R.drawable.ic_rain
        }
    )
    val weathericonBackground = context.drawable(
        when (weather.state) {
            Weather.State.Sunny -> R.drawable.sun_back
            Weather.State.Cloudy -> R.drawable.cloud_back
            Weather.State.Snow -> R.drawable.snow_back
            Weather.State.Rain -> R.drawable.rain_back
        }
    )
    val weatherState = context.resources.getString(
        when (weather.state) {
            Weather.State.Sunny -> R.string.cd_weather_state_sun
            Weather.State.Cloudy -> R.string.cd_weather_state_cloud
            Weather.State.Snow -> R.string.cd_weather_state_snow
            Weather.State.Rain -> R.string.cd_weather_state_rain
        }
    )
}