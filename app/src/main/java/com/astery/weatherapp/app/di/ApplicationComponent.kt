package com.astery.weatherapp.app.di

import android.content.Context
import com.astery.weatherapp.app.App
import com.astery.weatherapp.storage.local.LocalDataStorage
import com.astery.weatherapp.storage.local.LocalDataStorageImpl
import com.astery.weatherapp.storage.local.db.AppDatabase
import com.astery.weatherapp.storage.preferences.Preferences
import com.astery.weatherapp.storage.remote.RemoteDataStorage
import com.astery.weatherapp.storage.remote.RemoteDataStorageImpl
import com.astery.weatherapp.storage.remote.retrofit.weather.WeatherRetrofitInstance
import com.astery.weatherapp.storage.repository.Repository
import com.astery.weatherapp.storage.repository.RepositoryImpl
import com.astery.weatherapp.ui.favCities.FavCitiesFragment
import com.astery.weatherapp.ui.searchCities.SearchCitiesFragment
import com.astery.weatherapp.ui.weatherToday.WeatherTodayFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ContextDependAppModule::class])
interface ApplicationComponent {
    fun inject(fragment: WeatherTodayFragment)
    fun inject(fragment: FavCitiesFragment)
    fun inject(fragment: SearchCitiesFragment)
}

val Context.appComponent:ApplicationComponent
    get() = when(this) {
        is App -> this.appComponent
        else -> this.applicationContext.appComponent
    }

