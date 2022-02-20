package com.astery.weatherapp.storage.repository

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.Location
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.StateCompleted
import com.astery.weatherapp.model.state.StateGotNothing
import com.astery.weatherapp.model.state.StateResult
import com.astery.weatherapp.storage.local.LocalDataStorage
import com.astery.weatherapp.storage.preferences.LastCityId
import com.astery.weatherapp.storage.preferences.Preferences
import com.astery.weatherapp.storage.remote.RemoteDataStorage
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction1

class RepositoryImpl @Inject constructor(
    private val remote: RemoteDataStorage,
    private val local: LocalDataStorage,
    private val prefs: Preferences
) : Repository {
    override suspend fun getCurrentWeather(city: City): StateResult<WeatherData> {
        return getFromRemoteAndSave(
            city,
            remote::getCurrentWeather,
            local::getCurrentWeather,
            local::addWeatherData
        )
    }

    override suspend fun getCachedWeather(city: City): StateResult<WeatherData> {
        local.getCurrentWeather(city)?.let {
            return StateCompleted(it, true)
        }
        return StateGotNothing()
    }

    /** get city by location or get the city from the last saved location from cache*/
    override suspend fun getCity(location: Location): StateResult<City> {
        val result = getFromRemoteAndSave(
            location,
            remote::getCity, null, local::addCity
        )
        return if (result !is StateCompleted<City>) {
            val lastId = prefs.get(LastCityId(null))
            if (lastId == null) {
                result
            } else {
                local.getCity(lastId)
            }
        } else {
            prefs.set(LastCityId(result.result.id))
            result
        }
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
        KSuspendFunction1<B, StateResult<T>>, localGet: KSuspendFunction1<B, T?>?, localSet:
        KSuspendFunction1<T, Unit>
    ): StateResult<T> {
        val remoteResult = remoteGet(matcher)
        // if got data from result - cache them
        if (remoteResult is StateCompleted<T>) {
            localSet(remoteResult.result)
            return remoteResult
        }

        // if not, get from local (if it possible)
        val result = localGet?.invoke(matcher)
        return if (result != null)
            StateCompleted(result, true)
        else
            remoteResult


    }

}