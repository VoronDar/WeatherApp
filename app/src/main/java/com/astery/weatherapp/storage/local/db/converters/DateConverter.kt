package com.astery.weatherapp.storage.local.db.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDb(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun toClass(data: Long): Date {
        return Date(data)
    }
}