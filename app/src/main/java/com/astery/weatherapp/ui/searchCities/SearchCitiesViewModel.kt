package com.astery.weatherapp.ui.searchCities

import androidx.lifecycle.ViewModel
import com.astery.weatherapp.storage.repository.Repository
import javax.inject.Inject

class SearchCitiesViewModel @Inject constructor(private val repository: Repository): ViewModel()