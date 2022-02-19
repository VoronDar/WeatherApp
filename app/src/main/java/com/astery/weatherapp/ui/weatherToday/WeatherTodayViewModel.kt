package com.astery.weatherapp.ui.weatherToday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astery.weatherapp.model.City
import com.astery.weatherapp.storage.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class WeatherTodayViewModel constructor(city: City?, private val repository: Repository) :
    ViewModel() {

    class Factory @Inject constructor(private val repository: Repository) {
        fun create(city:City?): WeatherTodayViewModel = WeatherTodayViewModel(city, repository)
    }
}

