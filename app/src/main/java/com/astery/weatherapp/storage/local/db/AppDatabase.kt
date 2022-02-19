package com.astery.weatherapp.storage.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData

@Database(
    entities = [City::class, WeatherData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database"
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}