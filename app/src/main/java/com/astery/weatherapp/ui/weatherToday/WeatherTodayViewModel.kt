package com.astery.weatherapp.ui.weatherToday

import androidx.lifecycle.ViewModel
import com.astery.weatherapp.storage.repository.Repository
import javax.inject.Inject

class WeatherTodayViewModel @Inject constructor(private val repository: Repository): ViewModel()