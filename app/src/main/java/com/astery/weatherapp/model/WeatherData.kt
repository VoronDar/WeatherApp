package com.astery.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherData(@PrimaryKey val id:Int)