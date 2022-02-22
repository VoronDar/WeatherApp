package com.astery.weatherapp.ui.fragments.citiesList

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.ui.base.adapter.MarginItemDecoration
import com.astery.weatherapp.ui.customViews.loadState.LoadStateView
import timber.log.Timber

/** render for fragment with cities list */
class CitiesObserver(
    private val recyclerView: RecyclerView,
    private val adapter: CitiesAdapter,
    private val loadingStateView: LoadStateView,
    onReloadListener: (() -> Unit) = {}
) : Observer<Result<List<WeatherData>>> {

    init {
        loadingStateView.onReloadListener = onReloadListener
        recyclerView.run {
            addItemDecoration(MarginItemDecoration())
            layoutManager =
                LinearLayoutManager(recyclerView.context!!, RecyclerView.VERTICAL, false)
        }
        recyclerView.adapter = adapter
    }


    override fun onChanged(result: Result<List<WeatherData>>?) {
        when (result) {
            is Idle -> renderLoading()
            is Loading -> renderLoading()
            is Completed<List<WeatherData>> -> renderComplete(result.result)
            is GotNothing -> renderNothing()
            else -> renderError()
        }
    }

    private fun renderNothing() {
        loadingStateView.changeState(LoadStateView.StateNothing(), recyclerView)
    }

    private fun renderLoading() {
        loadingStateView.changeState(LoadStateView.StateLoading(), recyclerView)
    }

    private fun renderError() {
        loadingStateView.changeState(LoadStateView.StateError(), recyclerView)
    }

    private fun renderComplete(weather: List<WeatherData>) {
        Timber.d("render ${weather.subList(0, if (weather.size > 5) 5 else weather.size)}")
        loadingStateView.changeState(LoadStateView.StateHide(), recyclerView)
        adapter.submitList(weather)
    }

}
