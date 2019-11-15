package com.bme.aut.parkingsearch.ui.activity

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.broadcast.NetworkChangeReceiver

class NetworkErrorActivity : BaseActivity() {

    private var networkChangeReceiver: NetworkChangeReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_error)

        init()
    }

    override fun onStart() {
        super.onStart()
        registerNetworkErrorBrodacastReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReceiver)

    }

    override fun onBackPressed() {
        finish() // ? ez kell ide
    }

    private fun init() {
        //beállítnai aview elemeket
    }

    private fun registerNetworkErrorBrodacastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    fun networkIsBack() {
        startMainActivity()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}