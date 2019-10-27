package com.bme.aut.parkingsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.viewModel.ParkingDetailsViewModel

class ParkingDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ParkingDetailsFragment()
    }

    private lateinit var viewModel: ParkingDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parking_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ParkingDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
