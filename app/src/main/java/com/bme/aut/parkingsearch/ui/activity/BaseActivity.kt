package com.bme.aut.parkingsearch.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bme.aut.parkingsearch.events.InitialEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onInitialEvent(event: InitialEvent) {
        //nothing
    }
}