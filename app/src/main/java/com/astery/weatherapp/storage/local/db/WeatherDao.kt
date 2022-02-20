package com.astery.weatherapp.storage.local.db

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.astery.weatherapp.model.pogo.WeatherEntity
import com.astery.weatherapp.storage.local.db.converters.WeatherStateConverter

@Dao
@TypeConverters(WeatherStateConverter::class)
interface WeatherDao{

    @Query("SELECT * from WEATHERENTITY WHERE id = :id")
    suspend fun getWeather(id:Int):WeatherEntity


    @Insert(onConflict = IGNORE)
    suspend fun addWeatherData(weather:WeatherEntity)

    @Update
    suspend fun updateWeatherData(weather:WeatherEntity)
}