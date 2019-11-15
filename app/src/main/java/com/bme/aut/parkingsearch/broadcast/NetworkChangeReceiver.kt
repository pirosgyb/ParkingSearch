package com.bme.aut.parkingsearch.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.bme.aut.parkingsearch.ui.activity.MainActivity
import com.bme.aut.parkingsearch.ui.activity.NetworkErrorActivity

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && isNetworkAvailable(context)) {
            if (context is NetworkErrorActivity) {
                context.networkIsBack()
            } else if (context is MainActivity) {
                context.networkIsGone() // ez kell?
            }
        } else {
            (context as? MainActivity)?.networkIsGone()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}