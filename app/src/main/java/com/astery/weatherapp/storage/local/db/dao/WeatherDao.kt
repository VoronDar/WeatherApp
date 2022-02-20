package com.astery.weatherapp.storage.local.db.dao

import androidx.room.*
import com.astery.weatherapp.model.pogo.WeatherEntity
import com.astery.weatherapp.storage.local.db.converters.DateConverter
import com.astery.weatherapp.storage.local.db.converters.WeatherStateConverter

@Dao
@TypeConverters(WeatherStateConverter::class, DateConverter::class)
interface WeatherDao: BaseDao<WeatherEntity> {
    @Query("SELECT * from WeatherEntity WHERE id = :id")
    suspend fun getWeather(id:String):WeatherEntity?

}