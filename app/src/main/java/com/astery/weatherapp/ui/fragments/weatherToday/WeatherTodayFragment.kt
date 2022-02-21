package com.astery.weatherapp.ui.fragments.weatherToday

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.astery.weatherapp.app.di.appComponent
import com.astery.weatherapp.databinding.BottomShiftGeoDialogueBinding
import com.astery.weatherapp.databinding.WeatherFragmentBinding
import com.astery.weatherapp.model.pogo.WeatherData
import com.astery.weatherapp.model.state.*
import com.astery.weatherapp.ui.base.BaseFragment
import com.astery.weatherapp.ui.base.BindingInflater
import com.astery.weatherapp.ui.customViews.loadState.LoadStateView
import com.astery.weatherapp.ui.utils.ArgumentsDelegate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialSharedAxis
import timber.log.Timber
import javax.inject.Inject


class WeatherTodayFragment : BaseFragment<WeatherFragmentBinding>() {

    private val locationProvider = LocationProvider(this)
    private val weather: WeatherData? by ArgumentsDelegate()
    private val viewModel: WeatherTodayViewModel by lazy {
        factory.create(weather, locationProvider)
    }

    @Inject
    lateinit var factory: WeatherTodayViewModel.Factory

    override fun inflateBinding(): BindingInflater<WeatherFragmentBinding> {
        return WeatherFragmentBinding::inflate
    }

    override fun inject() {
        context?.appComponent?.inject(this)
    }

    // MARK: listeners
    override fun setViewModelListeners() {
        viewModel.weather.observe(viewLifecycleOwner, WeatherObserver())
        viewModel.getActualFavourite()
    }

    override fun prepareUI() {
        bind.favButton.setOnClickListener { moveToFavourite() }
        bind.searchButton.setOnClickListener { moveToSearch() }
        bind.geoButton.setOnClickListener { openGeoPanel() }
        bind.loadingStateView.onReloadListener = viewModel::getWeatherData
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationProvider.registerForActivityResult()
    }

    // MARK: render
    private inner class WeatherObserver : Observer<Result<WeatherData>> {
        override fun onChanged(result: Result<WeatherData>?) {
            Timber.d("${result!!::class.simpleName}")
            when (result) {
                is Idle -> renderLoading()
                is Loading -> renderLoading()
                is Completed<*> -> {
                    Timber.d("${(result.result as WeatherData).weatherData!!.date.time}")
                    renderComplete(
                        WeatherDataForUI(
                            requireContext(),
                            result.result.weatherData!!,
                            result.result.city
                        )
                    )
                }
                is Error -> renderError(result.error)
                else -> throw IllegalStateException("got invalid result state ${result::class.simpleName}")
            }
        }

        private fun renderLoading() {
            bind.loadingStateView.changeState(LoadStateView.StateLoading())
            renderChangeVisibility(true)
        }

        private fun renderComplete(weather: WeatherDataForUI) {
            bind.loadingStateView.changeState(LoadStateView.StateHide())
            renderChangeVisibility(false)

            bind.run {
                city.text = weather.cityName
                temperature.text = weather.temperature
                feelTemperature.text = weather.feelsLike
                pressure.text = weather.pressure
                humidity.text = weather.humidity
                windSpeed.text = weather.windSpeed
                time.text = weather.timestamp
                weatherState.setImageDrawable(weather.weatherIcon)
                weatherState.contentDescription = weather.weatherState
                weatherStateBackground.setImageDrawable(weather.weathericonBackground)
                favIcon.init(weather.isCityFav) { isFavourite ->
                    viewModel.changeSelectedCityFav(isFavourite)
                }

            }
        }

        private fun renderError(t: Error.ResultError) {
            Timber.d("got error ${t.name}")
            bind.loadingStateView.changeState(LoadStateView.StateError())
            if (t == Error.ResultError.PermissionDenied) moveToSearch()
        }

        private fun renderChangeVisibility(isGone: Boolean) {
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            TransitionManager.beginDelayedTransition(bind.workPanel, sharedAxis)
            TransitionManager.beginDelayedTransition(bind.root, sharedAxis)
            bind.weatherGroup.isGone = isGone
            bind.weatherState.isGone = isGone
            bind.weatherStateBackground.isGone = isGone

        }
    }

    // I clear context based variable in location provider because I give localProvider to viewModel
    override fun onDetach() {
        super.onDetach()
        locationProvider.clearFragment()
    }

    // MARK: navigation
    private fun moveToSearch() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToSearchCitiesFragment())
    }

    private fun moveToFavourite() {
        findNavController().navigate(WeatherTodayFragmentDirections.actionWeatherTodayFragmentToFavCitiesFragment())
    }

    // MARK: geo
    private fun openGeoPanel() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val binding = BottomShiftGeoDialogueBinding.inflate(layoutInflater, null, false)

        bottomSheetDialog.setContentView(binding.root)
        binding.submit.setOnClickListener {
            bottomSheetDialog.cancel()
            moveToAppSettings()
        }
        binding.cancel.setOnClickListener { bottomSheetDialog.cancel() }

        bottomSheetDialog.show()
    }

    private fun moveToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", activity!!.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}
