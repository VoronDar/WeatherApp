package com.astery.weatherapp.ui.searchCities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.astery.weatherapp.app.appComponent
import com.astery.weatherapp.databinding.SearchCitiesFragmentBinding
import com.astery.weatherapp.ui.BaseFragment

class SearchCitiesFragment : BaseFragment() {
    private val bind: SearchCitiesFragmentBinding
        get() = _bind!! as SearchCitiesFragmentBinding

    private val viewModel: SearchCitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = SearchCitiesFragmentBinding.inflate(inflater, container, false)
        return bind.root
    }
}