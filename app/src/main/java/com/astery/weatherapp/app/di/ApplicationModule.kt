package com.astery.weatherapp.app.di

import com.astery.weatherapp.storage.local.LocalDataStorage
import com.astery.weatherapp.storage.local.LocalDataStorageImpl
import com.astery.weatherapp.storage.remote.RemoteDataStorage
import com.astery.weatherapp.storage.remote.RemoteDataStorageFake
import com.astery.weatherapp.storage.remote.RemoteDataStorageImpl
import com.astery.weatherapp.storage.remote.retrofit.cities.CitiesApi
import com.astery.weatherapp.storage.remote.retrofit.cities.CitiesRetrofitInstance
import com.astery.weatherapp.storage.repository.Repository
import com.astery.weatherapp.storage.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
interface ApplicationModule{

    @Binds
    fun bindRepository(repository: RepositoryImpl): Repository

    @Binds
    fun bindLocalStorage(storage: LocalDataStorageImpl): LocalDataStorage

    @Binds
    @Prod
    fun bindRemoteStorage(storage: RemoteDataStorageImpl): RemoteDataStorage

    @Binds
    @Fake
    fun bindFakeRemoteStorage(storage: RemoteDataStorageFake):RemoteDataStorage


}
