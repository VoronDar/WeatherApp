package com.astery.weatherapp.storage.local.db.converters

import androidx.room.TypeConverter
import com.astery.weatherapp.model.pogo.WeatherState

class WeatherStateConverter {
    @TypeConverter
    fun toDb(value: WeatherState?): Int {
        return value?.ordinal?: WeatherState.Sunny.ordinal
    }

    @TypeConverter
    fun toClass(data: Int): WeatherState {
        return WeatherState.values()[data]
    }
}