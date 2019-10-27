package com.bme.aut.parkingsearch.viewModel

import androidx.lifecycle.ViewModel
import com.bme.aut.parkingsearch.model.ParkingSpot
import com.bme.aut.parkingsearch.repository.Repository

class HomeViewModel : ViewModel() {

    fun getParkingSpots(completion: (parkingSpots: ArrayList<ParkingSpot>?, error: String?) -> Unit) {

        Repository.getParkingSpots { parkingSpots, error ->
            //Ha kell kapott adatokat átkonvertálni azt itt érdemes elvégezni a viewModelben
            completion(parkingSpots, error)
        }

    }

}
