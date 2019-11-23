package com.bme.aut.parkingsearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParkingSpot(
    val address: String? = null,
    val displayName: String? = null,
    val imageUrl: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Parcelable