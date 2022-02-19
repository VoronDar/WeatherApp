package com.astery.weatherapp.ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * base fragment for all of fragments in the app
 * has some identical logic like: destroying binding
 * */
open class BaseFragment : Fragment() {
    protected var _bind: ViewBinding? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

}