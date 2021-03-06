package com.astery.weatherapp.storage.repository

import com.astery.weatherapp.app.di.Prod
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Completed
import com.astery.weatherapp.model.state.Error
import com.astery.weatherapp.model.state.GotNothing
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.local.LocalDataStorage
import com.astery.weatherapp.storage.preferences.LastCityId
import com.astery.weatherapp.storage.preferences.Preferences
import com.astery.weatherapp.storage.remote.RemoteDataStorage
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction1

class RepositoryImpl @Inject constructor(
    @Prod private val remote: RemoteDataStorage,
    private val local: LocalDataStorage,
    private val prefs: Preferences
) : Repository {

    override suspend fun getCurrentWeather(city: City): Result<WeatherData> {
        return getFromRemoteAndSave(
            city,
            remote::getCurrentWeather,
            local::getCurrentWeather,
            local::addWeatherData
        )
    }

    override suspend fun getCachedWeather(city: City): Result<WeatherData> {
        local.getCurrentWeather(city)?.let {
            return Completed(it, true)
        }
        return GotNothing()
    }

    /** get city by location or get the city from the last saved location from cache*/
    override suspend fun getCity(location: Location): Result<City> {
        val result = getFromRemoteAndSave(
            location,
            remote::getCity, null, local::addCity
        )
        return if (result !is Completed<City>) {
            getLastViewedCity()
        } else {
            prefs.set(LastCityId(result.result.id))
            result
        }
    }

    override suspend fun getLastViewedCity(): Result<City> {
        val lastId = prefs.get(LastCityId(null))
        return if (lastId == null) {
            Error(Error.ResultError.UnexpectedBug)
        } else {
            local.getCity(lastId)
        }
    }

    override suspend fun getFavouriteCities() = local.getFavouriteCities()

    override suspend fun getCities(searchQuery: String): Result<List<WeatherData>> {
        val result = remote.getCities(searchQuery)
        if (result is Completed) {
            result.result.forEach {
                it.city.isFavourite = local.isFavourite(it.city)
            }
            return result
        }
        return local.getCities(searchQuery)
    }

    override suspend fun getCities(): Result<List<WeatherData>> {
        val result = remote.getTopCities()
        if (result is Completed) {
            result.result.forEach {
                it.city.isFavourite = local.isFavourite(it.city)
            }
            return result
        }
        return local.getCities()
    }

    override suspend fun setLastViewedCity(city: City) {
        prefs.set(LastCityId(city.id))
    }


    override suspend fun changeCityFavouriteState(city: City) {
        local.changeCityFavouriteState(city)
    }

    override suspend fun isCityFavourite(city: City): Boolean {
        return local.isFavourite(city) ?: false

    }

    /**
     * get from remote - save to local
     * if there is no data in remote - get from local
     * if there is no data in local - return error
     *
     * @param matcher - parameter for functions (like search temperature by city)
     * */
    private suspend fun <B, T> getFromRemoteAndSave(
        matcher: B, remoteGet:
        KSuspendFunction1<B, Result<T>>, localGet: KSuspendFunction1<B, T?>?, localSet:
        KSuspendFunction1<T, Unit>
    ): Result<T> {
        val remoteResult = remoteGet(matcher)
        // if got data from result - cache them
        if (remoteResult is Completed<T>) {
            localSet(remoteResult.result)
            return remoteResult
        }

        // if not, get from local (if it possible)
        val result = localGet?.invoke(matcher)
        return if (result != null)
            Completed(result, true)
        else
            remoteResult


    }

}
