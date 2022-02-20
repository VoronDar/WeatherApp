package com.astery.weatherapp.app.di

import com.astery.weatherapp.storage.local.LocalDataStorage
import com.astery.weatherapp.storage.local.LocalDataStorageImpl
import com.astery.weatherapp.storage.remote.RemoteDataStorage
import com.astery.weatherapp.storage.remote.RemoteDataStorageImpl
import com.astery.weatherapp.storage.repository.Repository
import com.astery.weatherapp.storage.repository.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface ApplicationModule{

    @Binds
    fun bindRepository(repository: RepositoryImpl): Repository

    @Binds
    fun bindLocalStorage(storage: LocalDataStorageImpl): LocalDataStorage

    @Binds
    fun bindRemoteStorage(storage: RemoteDataStorageImpl): RemoteDataStorage


}
