package com.astery.weatherapp.ui.customViews.loadState

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnCancel
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.astery.weatherapp.R
import com.astery.weatherapp.databinding.LoadingStateViewBinding
import com.astery.weatherapp.ui.utils.drawable

/** loading data placeholder with several states: loading, no data, error */
class LoadStateView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val bind: LoadingStateViewBinding =
        LoadingStateViewBinding.inflate(LayoutInflater.from(context), this, true)

    var state: LoadingState = StateHide()
        set(value) {
            field.stopShow(bind)
            field = value
            renderState()
        }

    /** error state has button 'reload'.*/
    var onReloadListener: (() -> Unit)? = null
        set(value) {
            field = value
            if (value != null) bind.reloadButton.setOnClickListener { value.invoke() }
        }

    /**
     * @param switcher view that replaced with this (one of them is gone and another is visible and vice versa)
     * */
    fun changeState(state: LoadingState, switcher: View? = null) {
        this.state = state
        switcher?.isVisible = state is StateHide
        isVisible = state !is StateHide
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        renderState()
    }

    private fun renderState() {
        bind.text.text = context.getString(state.textId)
        bind.icon.setImageDrawable(context.drawable(state.iconId))
        state.render(bind)
    }

    sealed class LoadingState {
        open val iconId: Int = R.drawable.ic_loading_state_loading
        open val textId: Int = R.string.loading_state_loading
        open fun render(bind: LoadingStateViewBinding) {}
        open fun stopShow(bind: LoadingStateViewBinding) {}
    }

    class StateLoading : LoadingState() {
        override val iconId: Int = R.drawable.ic_loading_state_loading
        override val textId: Int = R.string.loading_state_loading


        /** animation for infinite full rotation */
        private val rotationAnimator: ValueAnimator by lazy(LazyThreadSafetyMode.NONE) {
            val animator = ValueAnimator.ofFloat(0f, 360f)
            animator.repeatCount = ValueAnimator.INFINITE
            animator.duration = 700
            animator.interpolator = LinearInterpolator()
            animator
        }


        override fun render(bind: LoadingStateViewBinding) {
            rotationAnimator.addUpdateListener {
                val value = it.animatedValue as Float
                bind.icon.rotation = value
            }
            rotationAnimator.doOnCancel {
                bind.icon.rotation = 0f
            }
            rotationAnimator.start()
        }

        override fun stopShow(bind: LoadingStateViewBinding) {
            rotationAnimator.cancel()
        }
    }

    class StateNothing : LoadStateView.LoadingState() {
        override val iconId: Int = R.drawable.ic_loading_state_nothing
        override val textId: Int = R.string.loading_state_nothing
    }

    class StateError : LoadingState() {
        override val iconId: Int = R.drawable.ic_loading_state_error
        override val textId: Int = R.string.loading_state_error
        override fun render(bind: LoadingStateViewBinding) {
            bind.reloadButton.isVisible = true
        }

        override fun stopShow(bind: LoadingStateViewBinding) {
            bind.reloadButton.isInvisible = true
        }
    }

    class StateHide : LoadingState()
}