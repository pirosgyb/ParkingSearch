package com.bme.aut.parkingsearch.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.bme.aut.parkingsearch.model.ParkingSpot
import com.bme.aut.parkingsearch.repository.Repository
import com.google.android.gms.maps.model.LatLng

class HomeViewModel : ViewModel() {
    var lastLocation: Location? = null
    var searchedPosition: LatLng? = null

    fun getParkingSpots(completion: (parkingSpots: ArrayList<ParkingSpot>?, error: String?) -> Unit) {

        Repository.getParkingSpots { parkingSpots, error ->
            //Ha kell kapott adatokat átkonvertálni azt itt érdemes elvégezni a viewModelben
            completion(parkingSpots, error)
        }

    }

}
