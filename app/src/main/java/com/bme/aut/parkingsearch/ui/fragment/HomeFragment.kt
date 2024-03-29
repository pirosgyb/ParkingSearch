package com.bme.aut.parkingsearch.ui.fragment

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bme.aut.parkingsearch.R
import com.bme.aut.parkingsearch.enum.MarkerType
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.events.SearchClickedEvent
import com.bme.aut.parkingsearch.model.ParkingSpot
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.ui.activity.MainActivity
import com.bme.aut.parkingsearch.util.*
import com.bme.aut.parkingsearch.viewModel.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.greenrobot.eventbus.Subscribe


class HomeFragment : BaseFragment() {

    companion object {
        const val ARG_KEY = "HOME_FRAGMENT_ARG_KEY"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        initPlaceListener()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
        googleMap?.let {
            updateLastLocation()
        }
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
            NavigationManager.navigateToAddParking(viewModel.searchedAddress, findNavController())
        }

        currentPositionFAB.setOnClickListener {
            updateMap(
                shouldAnimate = true,
                currentPosition = viewModel.lastLocation,
                showingPosition = viewModel.lastLocation.toLatLng()
            )
        }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity as MainActivity)

    }

    private fun initMap() {
        mapView?.getMapAsync { googleMap ->
            this.googleMap = googleMap
            MapsInitializer.initialize(context)
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true

            checkPermission()
            updateLastLocation()

            googleMap.setOnInfoWindowClickListener { marker ->
                val parkingSpot = viewModel.getParkingSpotByLatLng(
                    latitude = marker.position.latitude,
                    longitude = marker.position.longitude
                )
                parkingSpot?.let { spot ->
                    NavigationManager.navigateToDetails(
                        spot,
                        findNavController()
                    )
                }
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                googleMap?.let {
                    updateLastLocation()
                }
            }
        }
    }

    private fun updateLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.lastLocation = location

                val shouldAnimate =
                    if (viewModel.lastLocation == null) true else !areLocationsEqual(
                        viewModel.lastLocation,
                        location
                    )

                var showingPosition = viewModel.lastLocation.toLatLng()

                viewModel.searchedAddress?.let {
                    showingPosition = it.getLatLng()
                }


                updateMap(
                    shouldAnimate = shouldAnimate,
                    currentPosition = viewModel.lastLocation,
                    showingPosition = showingPosition
                )

            }
        }
    }

    private fun updateMap(
        shouldAnimate: Boolean,
        currentPosition: Location?,
        showingPosition: LatLng?
    ) {

        googleMap?.clear()

        currentPosition?.toLatLng()
            ?.let {
                placeMarkerOnMap(
                    it,
                    getString(R.string.currentPosition),
                    MarkerType.CURRENT_POSITION
                )
            }
        viewModel.searchedAddress?.let {
            placeMarkerOnMap(
                it.getLatLng(),
                it.getAddressLine(0),
                MarkerType.SEARCHED_PLACE
            )
        }

        showingPosition?.let {
            if (shouldAnimate) {
                googleMap?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        showingPosition,
                        14f
                    )
                )
            } else {
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        showingPosition,
                        14f
                    )
                )
            }
        }

        viewModel.parkingSpots?.forEach {
            placeMarkerOnMap(
                position = LatLng(it.latitude, it.longitude),
                markerName = it.displayName ?: "",
                markerType = MarkerType.PARKING_SPOT
            )
        }
    }

    private fun placeMarkerOnMap(position: LatLng, markerName: String, markerType: MarkerType) {

        val icon: BitmapDescriptor? = when (markerType) {
            MarkerType.CURRENT_POSITION ->
                context.getMarkerIconFromDrawable(
                    R.drawable.ic_my_location_black_24dp,
                    R.color.blue
                )
            MarkerType.SEARCHED_PLACE ->
                BitmapDescriptorFactory.defaultMarker()
            MarkerType.PARKING_SPOT ->
                context.getMarkerIconFromDrawable(
                    R.drawable.ic_parking_spot_icon,
                    R.color.green
                )
        }

        val markerOptions = MarkerOptions()
            .position(position)
            .title(markerName)
            .icon(icon)

        googleMap?.addMarker(markerOptions)
    }

    private fun getPositionFrom(address: String): Address? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(address, 1)

        if (addresses == null) {
            context.toast("Geocode api not working properly.\nTry again later.")
            return null
        }

        return if (addresses.isNotEmpty()) {
            addresses[0]
        } else {
            null
        }
    }

    @Subscribe
    fun onSearchClickedEvent(event: SearchClickedEvent) {
        val searchedAddress = getPositionFrom(event.address)
        if (searchedAddress != null) {
            viewModel.searchedAddress = searchedAddress
            updateMap(true, viewModel.lastLocation, viewModel.searchedAddress?.getLatLng())
        } else {
            context.toast("Not found this address: ${event.address}")
        }

    }

    fun addPlaces(parkingSpot: ParkingSpot?) {

        parkingSpot?.let {
            viewModel.parkingSpots.add(it)

            placeMarkerOnMap(
                position = LatLng(it.latitude, it.longitude),
                markerName = it.displayName ?: "",
                markerType = MarkerType.PARKING_SPOT
            )

        }
    }

    private fun initPlaceListener() {
        FirebaseDatabase.getInstance()
            .getReference("places")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val addresses = dataSnapshot.getValue<ParkingSpot>(ParkingSpot::class.java)
                    //postsAdapter.addPost(newPost)
                    addPlaces(addresses)
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }

}
