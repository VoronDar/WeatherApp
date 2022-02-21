package com.astery.weatherapp.ui.searchCities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Loading
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCitiesViewModel(private val repository: Repository) : ViewModel() {

    private val _cities: MutableLiveData<Result<List<WeatherData>>> =
        MutableLiveData(Loading())
    val cities: LiveData<Result<List<WeatherData>>>
        get() = _cities

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch {
            _cities.value = repository.getCities()
        }
    }

    fun getCities(searchQuery: String?) {
        _cities.value = Loading()

        if (searchQuery?.isNotEmpty() == true) {
            viewModelScope.launch {
                _cities.value = repository.getCities(searchQuery)
            }
        } else {
            getCities()
        }
    }

    class Factory @Inject constructor(private val repository: Repository) {
        fun create(): SearchCitiesViewModel =
            SearchCitiesViewModel(repository)
    }

}