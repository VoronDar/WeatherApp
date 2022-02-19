package com.astery.weatherapp.ui.favCities

import androidx.lifecycle.ViewModel
import com.astery.weatherapp.storage.repository.Repository
import javax.inject.Inject

class FavCitiesViewModel @Inject constructor(private val repository: Repository): ViewModel()