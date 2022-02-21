package com.astery.weatherapp.app.di

import android.content.Context
import com.astery.weatherapp.app.App
import com.astery.weatherapp.ui.fragments.favCities.FavCitiesFragment
import com.astery.weatherapp.ui.fragments.searchCities.SearchCitiesFragment
import com.astery.weatherapp.ui.fragments.weatherToday.WeatherTodayFragment
import dagger.Component
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

