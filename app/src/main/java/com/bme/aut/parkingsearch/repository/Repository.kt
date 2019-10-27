package com.bme.aut.parkingsearch.repository

import com.bme.aut.parkingsearch.model.ParkingSpot
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        apiDefinition.getParkingSpots().enqueue(object : Callback<ArrayList<ParkingSpot>> {

            override fun onResponse(
                call: Call<ArrayList<ParkingSpot>>,
                response: Response<ArrayList<ParkingSpot>>
            ) {
                if (response.code() == 200) {
                    completion(response.body(), null)
                } else {
                    completion(null, "Error occur")
                }

            }

            override fun onFailure(call: Call<ArrayList<ParkingSpot>>, t: Throwable) {
                completion(null, "Error occur")
            }

        })
    }

}