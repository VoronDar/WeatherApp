package com.astery.weatherapp.storage.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.astery.weatherapp.model.pogo.City

@Dao
interface CityDao : BaseDao<City> {
    @Query("SELECT * from CITY WHERE id = :id")
    suspend fun getCity(id: String): City?

    @Query("SELECT * from CITY WHERE isFavourite = :isFavourite")
    suspend fun getFavourite(isFavourite: Boolean): List<City>
}