package com.astery.weatherapp.storage.local

import com.astery.weatherapp.storage.local.db.AppDatabase
import javax.inject.Inject

class LocalDataStorageImpl @Inject constructor(appDatabase: AppDatabase):LocalDataStorage {
}