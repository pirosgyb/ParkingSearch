package com.bme.aut.parkingsearch.util

import android.app.Activity
import android.content.Context
import android.location.Location
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng

fun Context?.toast(text: String) {
    this?.let {
        Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
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

fun Location?.toLatLng(): LatLng? {
    this?.let {
        return LatLng(it.latitude, it.longitude)
    }
    return null
}

fun areLocationsEqual(location1: Location?, location2: Location?): Boolean {
    return location1?.longitude == location2?.longitude &&
            location1?.latitude == location2?.latitude
}

fun View?.hideSoftKeyboard() {
    this?.context?.let { context ->
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager?.let {
            inputMethodManager.hideSoftInputFromWindow(
                (context as? Activity)?.currentFocus?.windowToken,
                0
            )
        }
    }
}