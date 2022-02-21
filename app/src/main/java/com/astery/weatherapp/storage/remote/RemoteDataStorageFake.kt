package com.astery.weatherapp.storage.remote

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.Weather
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Completed
import com.astery.weatherapp.model.state.Error
import com.astery.weatherapp.model.state.Result
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RemoteDataStorageFake @Inject constructor(
) : RemoteDataStorage {
    override suspend fun getCurrentWeather(city: City): Result<WeatherData> {
        return Completed(
            WeatherData(
                City("1", "Москва", City.Country("1", "ara")), Weather(
                    "1", 12.4, 12.4, 12,
                    12.4, 12.4, 12,
                    Weather.State.Cloudy, Date(), 12
                )
            ), false
        )
    }

    override suspend fun getCity(location: Location): Result<City> {
        return Completed(City("1", "Москва", City.Country("1", "ara")), false)
    }

    override suspend fun getTopCities(): Result<List<WeatherData>> {
        val list = ArrayList<WeatherData>()
        for (i in 1..50) {
            list.add(WeatherData(City("$i", "Москва $i", City.Country("1", "ara")), null))
        }
        return Completed(list, false)
    }

    override suspend fun getCities(searchQuery: String): Result<List<WeatherData>> {
        return Error(Error.ResultError.UnexpectedBug)
    }
}