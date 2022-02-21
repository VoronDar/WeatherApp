package com.astery.weatherapp.ui.searchCities

import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.SearchCitiesFragmentBinding
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.ui.BaseFragment
import com.astery.weatherapp.ui.BindingInflater
import com.astery.weatherapp.ui.citiesList.CitiesAdapter
import com.astery.weatherapp.ui.citiesList.CitiesObserver
import javax.inject.Inject

class SearchCitiesFragment : BaseFragment<SearchCitiesFragmentBinding>() {
    private val viewModel: SearchCitiesViewModel by lazy {
        factory.create()
    }
    @Inject
    lateinit var factory: SearchCitiesViewModel.Factory

    private val adapter: CitiesAdapter = CitiesAdapter(listOf(), this::moveToWeather)


    override fun inflateBinding(): BindingInflater<SearchCitiesFragmentBinding> {
        return SearchCitiesFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    override fun setViewModelListeners() {
        viewModel.cities.observe(
            viewLifecycleOwner, CitiesObserver(
                bind.recyclerView, adapter, bind.loadStateView
            )
        )
    }

    override fun prepareUI() {
        bind.back.setOnClickListener { activity?.onBackPressed() }
        bind.searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                bind.search.performClick()
                false
            } else false
        }


        bind.search.setOnClickListener {
            hideSearchKeyboard()
            viewModel.getCities(bind.searchText.text.toString())
        }
    }

    private fun moveToWeather(city: WeatherData) {
        findNavController().navigate(
            SearchCitiesFragmentDirections.actionSearchCitiesFragmentToWeatherTodayFragment(
                city
            )
        )
    }


    private fun hideSearchKeyboard() {
        val imm =
            activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(bind.searchText.windowToken, 0)
    }

}