package com.astery.weatherapp.ui.fragments.baseChangeFav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astery.weatherapp.model.pogo.City
import com.astery.weatherapp.storage.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/** base viewModel for fragments that can add cities to favourite */
abstract class ChangeFavViewModel(
    protected val repository: Repository,
    protected val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun changeCityFavouriteState(city: City) {
        viewModelScope.launch(dispatcher) {
            repository.changeCityFavouriteState(city)
        }
    }

}

