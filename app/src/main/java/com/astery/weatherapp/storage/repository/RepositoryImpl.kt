package com.astery.weatherapp.storage.repository

import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.ResultError
import com.astery.weatherapp.model.state.StateCompleted
import com.astery.weatherapp.model.state.StateError
import com.astery.weatherapp.model.state.StateResult
import com.astery.weatherapp.storage.local.LocalDataStorage
import com.astery.weatherapp.storage.remote.RemoteDataStorage
import com.astery.weatherapp.ui.weatherToday.LocationProvider
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction1

class RepositoryImpl @Inject constructor(
    private val remote: RemoteDataStorage,
    private val local: LocalDataStorage
) : Repository {
    override suspend fun getCurrentWeather(city: City): StateResult<WeatherData> {
        return getFromRemoteAndSave(
            city,
            remote::getCurrentWeather,
            local::getCurrentWeather,
            local::addWeatherData
        )
    }

    override suspend fun getCity(location: LocationProvider.Location): StateResult<City> {
        return StateError(ResultError.UnexpectedBug)
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
        KSuspendFunction1<B, StateResult<T>>, localGet: KSuspendFunction1<B, T>, localSet:
        KSuspendFunction1<T, Unit>
    ): StateResult<T> {
        val remoteResult = remoteGet(matcher)
        // if got data from result - cache them
        if (remoteResult is StateCompleted<T>) {
            localSet(remoteResult.result)
            return remoteResult
        }

        // if not, get from local
        val result = localGet(matcher)
        return if (result != null)
            StateCompleted(result, true)
        else
            remoteResult


    }

}