package com.astery.weatherapp.app

import android.app.Application
import android.content.Context
import com.astery.weatherapp.BuildConfig
import com.astery.weatherapp.app.di.ApplicationComponent
import com.astery.weatherapp.app.di.ContextDependAppModule
import com.astery.weatherapp.app.di.DaggerApplicationComponent
import timber.log.Timber

class App: Application() {
    val appComponent:ApplicationComponent by lazy{
        DaggerApplicationComponent.builder()
            .contextDependAppModule(ContextDependAppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
