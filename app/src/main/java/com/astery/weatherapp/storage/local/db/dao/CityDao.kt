package com.astery.weatherapp.storage.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.astery.weatherapp.model.pogo.City

@Dao
interface CityDao : BaseDao<City> {
    @Query("SELECT * from CITY WHERE id = :id")
    suspend fun getCity(id: String): City?

    @Query("SELECT * from CITY WHERE isFavourite = :isFavourite")
    suspend fun getFavourite(isFavourite: Boolean): List<City>

    @Query("SELECT  * FROM City JOIN CityFTS ON City.name = CityFts.name WHERE (CityFts MATCH '*' || :key || '*' )")
    suspend fun getCities(key: String): List<City>

    @Query("SELECT  * FROM City")
    suspend fun getCities(): List<City>


}