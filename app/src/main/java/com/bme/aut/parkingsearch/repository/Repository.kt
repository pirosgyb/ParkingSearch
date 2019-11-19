package com.bme.aut.parkingsearch.repository

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.bme.aut.parkingsearch.ParkingSearchApplication
import com.bme.aut.parkingsearch.model.ParkingSpot
import com.bme.aut.parkingsearch.viewModel.AddParkingViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

object Repository {

    private lateinit var baseUrlValue: String
    private lateinit var apiDefinition: ApiDefinition

    fun init() {
        initNetwork()
    }

    private fun initNetwork() {
        baseUrlValue = "http://valami.hu"

        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(baseUrlValue)
            .build()

        apiDefinition = retrofit.create(ApiDefinition::class.java)
    }

    //Ilyen formában csináltam eddig az apihívásokat
    fun getParkingSpots(completion: (parkingSpots: ArrayList<ParkingSpot>?, error: String?) -> Unit) {

//        apiDefinition.getParkingSpots().enqueue(object : Callback<ArrayList<ParkingSpot>> {
//
//            override fun onResponse(
//                call: Call<ArrayList<ParkingSpot>>,
//                response: Response<ArrayList<ParkingSpot>>
//            ) {
//                if (response.code() == 200) {
//                    completion(response.body(), null)
//                } else {
//                    completion(null, "Error occur")
//                }
//
//            }
//
//            override fun onFailure(call: Call<ArrayList<ParkingSpot>>, t: Throwable) {
//                completion(null, "Error occur")
//            }
//
//        })
    }

}