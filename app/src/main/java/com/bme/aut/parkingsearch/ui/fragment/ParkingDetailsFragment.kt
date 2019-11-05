package com.bme.aut.parkingsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.viewModel.ParkingDetailsViewModel
import kotlinx.android.synthetic.main.fragment_add_parking.*

class ParkingDetailsFragment : BaseFragment() {

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
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(ParkingDetailsViewModel::class.java)
        toolbarView?.bind(ToolbarModel(type = ToolbarType.NAVIGATE_BACK, title = "Details"))
    }

}
