package com.bme.aut.parkingsearch.repository

import com.bme.aut.parkingsearch.model.ParkingSpot
import retrofit2.Call
import retrofit2.http.GET

interface ApiDefinition {

    //Valami ilyen formájú függvényekre lesz majd itt szükségünk
    @GET("parkingspots")
    fun getParkingSpots(): Call<ArrayList<ParkingSpot>>

}