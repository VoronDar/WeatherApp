package com.astery.weatherapp.storage.local.db.dao

import androidx.room.*
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import java.lang.Math.abs
import kotlin.math.pow

@Dao
interface CityDao: BaseDao<City> {
    @Query("SELECT * from CITY WHERE id = :id")
    suspend fun getCity(id:String): City?
}