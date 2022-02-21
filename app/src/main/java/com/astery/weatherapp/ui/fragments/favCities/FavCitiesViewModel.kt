package com.astery.weatherapp.ui.fragments.favCities

import androidx.lifecycle.*
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Completed
import com.astery.weatherapp.model.state.Idle
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.repository.Repository
import com.astery.weatherapp.ui.fragments.baseChangeFav.ChangeFavViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class FavCitiesViewModel(repository: Repository,  dispatcher: CoroutineDispatcher) : ChangeFavViewModel(repository, dispatcher) {

    private val _cities: MutableLiveData<Result<List<WeatherData>>> =
        MutableLiveData(Idle())
    val cities: LiveData<Result<List<WeatherData>>>
        get() = _cities

    init {
        getFavouriteCities()
    }

    private fun getFavouriteCities() {
        viewModelScope.launch(dispatcher) {
            if (_cities.value is Completed) return@launch
            _cities.postValue(repository.getFavouriteCities())
        }
    }

    class Factory @Inject constructor(private val repository: Repository, private val dispatcher: CoroutineDispatcher) {
        fun create(): FavCitiesViewModel =
            FavCitiesViewModel(repository, dispatcher)
    }

}