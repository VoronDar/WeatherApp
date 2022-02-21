package com.astery.weatherapp.ui.citiesList

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.ui.loadState.LoadStateView

/** render for fragment with cities list */
class CitiesObserver(
    private val recyclerView: RecyclerView,
    private val adapter: CitiesAdapter,
    private val loadingStateView: LoadStateView,
    onReloadListener: (() -> Unit) = {}
) : Observer<Result<List<WeatherData>>> {

    init {
        loadingStateView.onReloadListener = onReloadListener
    }


    override fun onChanged(result: Result<List<WeatherData>>?) {
        when (result) {
            is Idle -> renderLoading()
            is Loading -> renderLoading()
            is Completed<*> -> renderComplete(result.result as List<WeatherData>)
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
        loadingStateView.changeState(LoadStateView.StateHide(), recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context!!, RecyclerView.VERTICAL, false)
        adapter.submitList(weather)
        recyclerView.adapter = adapter
    }

}
