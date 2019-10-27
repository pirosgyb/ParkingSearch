package com.bme.aut.parkingsearch

import android.app.Application
import com.bme.aut.parkingsearch.repository.Repository

class ParkingSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Repository.init()
    }

}