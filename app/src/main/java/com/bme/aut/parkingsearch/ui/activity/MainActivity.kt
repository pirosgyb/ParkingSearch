package com.bme.aut.parkingsearch.ui.activity

import android.content.Intent
import android.os.Bundle
import com.bme.aut.parkingsearch.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun networkIsGone() {
        val intent = Intent(this, NetworkErrorActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}
