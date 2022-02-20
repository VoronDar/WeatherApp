package com.astery.weatherapp.ui.favCities

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.FavCitiesFragmentBinding
import com.astery.weatherapp.model.pogo.Weather
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater
import timber.log.Timber
import javax.inject.Inject

class FavCitiesFragment : BaseFragment<FavCitiesFragmentBinding>() {
    @Inject
    lateinit var viewModelFactory: FavCitiesViewModel.Factory
    private val viewModel: FavCitiesViewModel by lazy {
        viewModelFactory.create()
    }
    private val adapter: FavCitiesAdapter by lazy(mode = LazyThreadSafetyMode.NONE) {
        // it assumes that adapter will be called only if the viewModel returns list
        FavCitiesAdapter(
            (viewModel.cities.value!! as Completed<List<WeatherData>>).result,
            this::moveToWeather
        )
    }


    override fun inflateBinding(): BindingInflater<FavCitiesFragmentBinding> {
        return FavCitiesFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
        viewModel.cities.observe(viewLifecycleOwner, FavCitiesObserver())
    }

    override fun prepareUI() {
    }

    private fun moveToWeather(data: WeatherData) {
        findNavController().navigate(
            FavCitiesFragmentDirections.actionFavCitiesFragmentToWeatherTodayFragment(
                data
            )
        )

    }


    // MARK: render
    private inner class FavCitiesObserver : Observer<Result<List<WeatherData>>> {
        override fun onChanged(result: Result<List<WeatherData>>?) {
            Timber.d("got result ${result}")
            when (result) {
                is Idle -> renderLoading()
                is Loading -> renderLoading()
                is Completed<*> -> renderComplete(result.result as List<WeatherData>)
                is GotNothing -> renderNothing()
            }
        }

        private fun renderNothing() {

        }

        private fun renderLoading() {
        }

        private fun renderComplete(weather: List<WeatherData>) {
            bind.run {
                recyclerView.layoutManager =
                    LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
                adapter.submitList(weather)
                recyclerView.adapter = adapter
            }
        }

    }

}