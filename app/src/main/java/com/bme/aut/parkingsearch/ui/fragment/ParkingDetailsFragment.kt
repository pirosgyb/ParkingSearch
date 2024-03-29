package com.bme.aut.parkingsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.util.visible
import com.bme.aut.parkingsearch.viewModel.ParkingDetailsViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_parking_details.*

class ParkingDetailsFragment : BaseFragment() {

    companion object {
        const val ARG_KEY = "PARKING_DETAILS_ARG_KEY"
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
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(ParkingDetailsViewModel::class.java)
        toolbarView?.bind(ToolbarModel(type = ToolbarType.NAVIGATE_BACK, title = "Details"))

        viewModel.parkingSpotToShow = arguments?.getParcelable(ARG_KEY)

        addressEditText?.text = viewModel.parkingSpotToShow?.address ?: ""

        viewModel.parkingSpotToShow?.imageUrl?.let {
            photoImageView.visible()
            Glide.with(context!!)
                .load(it)
                .into(photoImageView)
        }

    }

}
