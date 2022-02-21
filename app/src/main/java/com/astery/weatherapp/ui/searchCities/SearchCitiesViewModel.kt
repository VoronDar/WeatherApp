package com.astery.weatherapp.ui.searchCities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.Loading
import com.astery.weatherapp.model.state.Result
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import javax.inject.Inject

class SearchCitiesViewModel(private val repository: Repository, private val dispatcher: CoroutineDispatcher) : ViewModel() {

    private val _cities: MutableLiveData<Result<List<WeatherData>>> =
        MutableLiveData(Loading())
    val cities: LiveData<Result<List<WeatherData>>>
        get() = _cities

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch(dispatcher) {
            _cities.postValue(repository.getCities())
        }
    }

    fun changeCityFavouriteState(city: City){
        viewModelScope.launch(dispatcher){
            repository.changeCityFavouriteState(city)
        }
    }

    fun getCities(searchQuery: String?) {
        _cities.postValue(Loading())

        if (searchQuery?.isNotEmpty() == true) {
            viewModelScope.launch(dispatcher) {
                _cities.postValue(repository.getCities(prepareQuery(searchQuery)))
            }
        } else {
            getCities()
        }
    }

    /** capitalize all words. It is required to make TTS work (db stores capitalized words)  */
    private fun prepareQuery(searchQuery: String):String{
        val words = searchQuery.split(" ")
        val stringBuilder = StringBuilder()
        words.forEach { string ->
            stringBuilder.append(string.replaceFirstChar {
                it.uppercase()
            })
        }
        return stringBuilder.toString()
    }

    class Factory @Inject constructor(private val repository: Repository, private val dispatcher: CoroutineDispatcher) {
        fun create(): SearchCitiesViewModel =
            SearchCitiesViewModel(repository, dispatcher)
    }

}