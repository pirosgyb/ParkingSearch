package com.bme.aut.parkingsearch.viewModel

import android.location.Address
import android.location.Location
import androidx.lifecycle.ViewModel
import com.bme.aut.parkingsearch.model.ParkingSpot

class HomeViewModel : ViewModel() {
    var lastLocation: Location? = null
    var searchedAddress: Address? = null
    var parkingSpots = ArrayList<ParkingSpot>()

    fun getParkingSpotByLatLng(latitude: Double, longitude: Double): ParkingSpot? {
        parkingSpots.forEach { spot ->
            if (spot.latitude == latitude && spot.longitude == longitude) {
                return spot
            }
        }

        return null
    }
}
