package com.bme.aut.parkingsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.viewModel.AddParkingViewModel
import kotlinx.android.synthetic.main.fragment_add_parking.*

class AddParkingFragment : BaseFragment() {

    companion object {
        const val ARG_KEY = "ADD_PARKING_ARG_KEY"
    }

    private lateinit var viewModel: AddParkingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_parking, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(AddParkingViewModel::class.java)
        toolbarView?.bind(
            ToolbarModel(
                type = ToolbarType.NAVIGATE_BACK,
                title = "Add new parking spot"
            )
        )
        viewModel.address = arguments?.getParcelable(ARG_KEY)
        addressEditText?.setText(viewModel.address?.getAddressLine(0), TextView.BufferType.EDITABLE)
    }

}