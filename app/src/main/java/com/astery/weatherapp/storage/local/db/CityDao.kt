package com.astery.weatherapp.storage.local.db

import androidx.room.*
import com.astery.weatherapp.model.pogo.City

@Dao
interface CityDao{
    @Query("SELECT * from CITY WHERE id = :id")
    suspend fun getCity(id:Int): City

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: City)

    @Update
    suspend fun updateCity(city: City)
}