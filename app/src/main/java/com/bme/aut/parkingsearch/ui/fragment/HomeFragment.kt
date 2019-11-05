package com.bme.aut.parkingsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.events.SearchClickedEvent
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.util.NavigationManager
import com.bme.aut.parkingsearch.util.toast
import com.bme.aut.parkingsearch.viewModel.HomeViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.greenrobot.eventbus.Subscribe

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.mapView
        mapView?.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        googleMap?.let {
            //TODO: checkLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
        mapView = null
        googleMap = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        toolbarView?.bind(ToolbarModel(type = ToolbarType.SEARCH))
        initMap()

        addParkingFAB.setOnClickListener {
            NavigationManager.navigateToAddParking(findNavController())
        }

    }

    private fun initMap() {
        MapsInitializer.initialize(context)
        mapView?.getMapAsync { googleMap ->
            this.googleMap = googleMap
            requestData()
        }
    }

    private fun requestData() {
        //TODO request data
    }


    @Subscribe
    fun onSearchClickedEvent(event: SearchClickedEvent) {
        context.toast(event.address)
        //TODO: move to event.address
    }

}
