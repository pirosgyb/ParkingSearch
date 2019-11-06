package com.bme.aut.parkingsearch.util

import android.content.Context
import android.location.Location
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng

fun Context?.toast(text: String) {
    this?.let {
        Toast.makeText(it, text, Toast.LENGTH_LONG).show()
    }
}

fun View?.visible() {
    this?.let {
        it.visibility = View.VISIBLE
    }
}

fun View?.gone() {
    this?.let {
        it.visibility = View.GONE
    }
}

fun Location.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}