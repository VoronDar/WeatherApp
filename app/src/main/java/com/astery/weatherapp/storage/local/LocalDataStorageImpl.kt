package com.astery.weatherapp.storage.local

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Weather
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Completed
import com.astery.weatherapp.model.state.Error
import com.astery.weatherapp.model.state.GotNothing
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.local.db.AppDatabase
import com.astery.weatherapp.storage.local.db.dao.BaseDao
import javax.inject.Inject

class LocalDataStorageImpl @Inject constructor(private val appDatabase: AppDatabase) :
    LocalDataStorage {
    override suspend fun getCurrentWeather(city: City): WeatherData? {
        val weatherEntity = appDatabase.weatherDao().getWeather(city.id)
        return if (weatherEntity != null)
            WeatherData(city, weatherEntity)
        else null
    }

    override suspend fun addWeatherData(weather: WeatherData) {
        (appDatabase.weatherDao() as BaseDao<Weather>).insert(weather.weatherData!!)
        (appDatabase.cityDao() as BaseDao<City>).insert((weather.city))
    }

    override suspend fun addCity(city: City) {
        (appDatabase.cityDao() as BaseDao<City>).insert(city)
    }

    override suspend fun getCity(lastId: String): Result<City> {
        val city = appDatabase.cityDao().getCity(lastId)
        return if (city != null)
            Completed(city, true)
        else Error(Error.ResultError.UnexpectedBug)


    }

    override suspend fun getFavouriteCities(): Result<List<WeatherData>> {
        return getCities(appDatabase.cityDao().getFavourite(true))
    }

    override suspend fun getCities(): Result<List<WeatherData>> {
        val cities = appDatabase.cityDao().getCities()
        if (cities.isEmpty()) return Error(Error.ResultError.UnexpectedBug)
        val weatherData = mutableListOf<WeatherData>()
        cities.forEach {
            weatherData.add(WeatherData(it, null))
        }
        return Completed(weatherData, true)
    }
    override suspend fun getCities(searchQuery: String): Result<List<WeatherData>> {
        return getCities(appDatabase.cityDao().getCities(searchQuery))
    }

    override suspend fun changeCityFavouriteState(city: City) {
        (appDatabase.cityDao() as BaseDao<City>).insert(city)
    }

    override suspend fun isFavourite(city: City): Boolean? {
        return appDatabase.cityDao().isFavourite(city.id)
    }

    private fun getCities(cities:List<City>):Result<List<WeatherData>>{
        if (cities.isEmpty()) return GotNothing()
        val weatherData = mutableListOf<WeatherData>()
        cities.forEach {
            weatherData.add(WeatherData(it, null))
        }
        return Completed(weatherData, true)
    }

}