package com.bme.aut.parkingsearch.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.location.Location
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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

fun Context?.getMarkerIconFromDrawable(drawableId: Int, colorId: Int): BitmapDescriptor? {
    this?.let { context_ ->
        val canvas = Canvas()
        val drawable = ContextCompat.getDrawable(context_, drawableId)

        drawable?.let {
            drawable.setTint(ContextCompat.getColor(context_, colorId))
            drawable.setTintMode(PorterDuff.Mode.SRC_ATOP)

            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            canvas.setBitmap(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }

        return null

    }
    return null

}