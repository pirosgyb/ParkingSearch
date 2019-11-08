package com.bme.aut.parkingsearch.ui.fragment

import androidx.fragment.app.Fragment
import com.bme.aut.parkingsearch.events.InitialEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onInitialEvent(event: InitialEvent) {
        //nothing
    }

}