package com.astery.weatherapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.google.android.material.transition.MaterialSharedAxis.Y

/**
 * base fragment for all of fragments in the app
 * has some identical logic like: creating and destroying builder
 * */
abstract class BaseFragment<B : ViewBinding> : Fragment() {
    private var _bind: B? = null
    protected val bind: B
        get() = _bind!!


    /** get a function that inflates a viewBinding */
    protected abstract fun inflateBinding(): BindingInflater<B>
    protected abstract fun inject()

    /** start observe viewModel variables. called after prepareUI in onViewCreated */
    protected abstract fun setViewModelListeners()

    /** attach adapters to recyclerView, set onClickListeners. called in onViewCreated */
    protected open fun prepareUI() {}


    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setTransitionAnimation()
    }

    private fun setTransitionAnimation() {
        enterTransition = MaterialSharedAxis(Y, /* forward= */ true)
        returnTransition = MaterialSharedAxis(Y, /* forward= */ false)
        exitTransition = MaterialSharedAxis(Y, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(Y, /* forward= */ false)
    }

    // MARK: binding lifecycle
    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = inflateBinding().invoke(inflater, container, false)
        return bind.root
    }


    final override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    // MARK: start actions
    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUI()
        setViewModelListeners()
    }


}

/** typealias for an inflater function */
typealias BindingInflater<B> = (inflater: LayoutInflater, container: ViewGroup?, isAttachToParent: Boolean) -> B
