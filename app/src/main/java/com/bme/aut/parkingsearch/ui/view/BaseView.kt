package com.bme.aut.parkingsearch.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bme.aut.parkingsearch.events.InitialEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        EventBus.getDefault().register(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onInitialEvent(event: InitialEvent) {
        //nothing
    }
}