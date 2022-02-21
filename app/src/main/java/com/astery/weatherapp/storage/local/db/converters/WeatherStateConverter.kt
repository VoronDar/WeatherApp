package com.astery.weatherapp.storage.local.db.converters

import androidx.room.TypeConverter
import com.astery.weatherapp.model.pogo.Weather

class WeatherStateConverter {
    @TypeConverter
    fun toDb(value: Weather.State?): Int {
        return value?.ordinal ?: Weather.State.Sunny.ordinal
    }

    @TypeConverter
    fun toClass(data: Int): Weather.State {
        return Weather.State.values()[data]
    }
}