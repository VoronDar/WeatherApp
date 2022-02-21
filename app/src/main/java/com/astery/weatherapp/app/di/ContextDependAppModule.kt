package com.astery.weatherapp.app.di

import android.content.Context
import com.astery.weatherapp.storage.local.db.AppDatabase
import com.astery.weatherapp.storage.preferences.Preferences
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class ContextDependAppModule(private val context: Context) {
    @Provides
    fun provideAppDatabase(): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providePreferences(): Preferences {
        return Preferences(context)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}
