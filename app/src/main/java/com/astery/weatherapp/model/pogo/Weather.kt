package com.astery.weatherapp.model.pogo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.astery.weatherapp.storage.local.db.converters.DateConverter
import com.astery.weatherapp.storage.local.db.converters.WeatherStateConverter
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
@TypeConverters(WeatherStateConverter::class, DateConverter::class)
data class Weather(
    // id weatherEntity = cityId
    @PrimaryKey val id: String,
    val temperature: Double,
    val feelLike: Double,
    val humidity: Int,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val state: State,
    val date: Date,
    val icon:Int
):Parcelable {

    enum class State {
        Sunny,
        Cloudy,
        Snow,
        Rain
    }
}
@Parcelize
data class WeatherData(val city: City, var weatherData: Weather?):Parcelable