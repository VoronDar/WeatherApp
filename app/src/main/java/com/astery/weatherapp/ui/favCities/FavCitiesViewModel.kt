package com.astery.weatherapp.ui.favCities

import androidx.lifecycle.*
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Completed
import com.astery.weatherapp.model.state.Idle
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class FavCitiesViewModel(private val repository: Repository, private val dispatcher: CoroutineDispatcher) : ViewModel() {

    private val _cities: MutableLiveData<Result<List<WeatherData>>> =
        MutableLiveData(Idle())
    val cities: LiveData<Result<List<WeatherData>>>
        get() = _cities

    init {
        getFavouriteCities()
    }

    fun changeCityFavouriteState(city: City){
        viewModelScope.launch(dispatcher){
            repository.changeCityFavouriteState(city)
        }
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