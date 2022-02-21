package com.astery.weatherapp.ui.favCities

import androidx.lifecycle.*
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Completed
import com.astery.weatherapp.model.state.Idle
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class FavCitiesViewModel(private val repository: Repository) : ViewModel() {

    private val _cities: MutableLiveData<Result<List<WeatherData>>> =
        MutableLiveData(Idle())
    val cities: LiveData<Result<List<WeatherData>>>
        get() = _cities

    init {
        getFavouriteCities()
    }

    private fun getFavouriteCities() {
        viewModelScope.launch {
            if (_cities.value is Completed) return@launch
            _cities.value = repository.getFavouriteCities()
        }
    }

    class Factory @Inject constructor(private val repository: Repository) {
        fun create(): FavCitiesViewModel =
            FavCitiesViewModel(repository)
    }

}