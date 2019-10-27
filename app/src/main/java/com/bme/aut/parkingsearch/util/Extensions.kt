package com.bme.aut.parkingsearch.util

import android.content.Context
import android.view.View
import android.widget.Toast

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