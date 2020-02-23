package com.example.dottchallenge.map.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dottchallenge.R
import com.example.dottchallenge.common.utils.PermissionUtils.isLocationGranted
import com.example.dottchallenge.common.utils.observeNonNull
import com.example.dottchallenge.common.utils.replaceFragment
import com.example.dottchallenge.common.utils.showPermissionDialog
import com.example.dottchallenge.common.utils.toLatLng
import com.example.dottchallenge.details.VenueDetailsFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : Fragment(), OnMapReadyCallback {

    companion object {
        private const val VALUE_TO_ZOOM = 15F

        fun create() = MapsFragment()
    }

    private lateinit var googleMap: GoogleMap

    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    private val mapsViewModel: MapsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_maps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireContext())

        with(mapsViewModel) {
            venuesLiveData.observeNonNull(viewLifecycleOwner) { venues ->
                venues.forEach { venue ->
                    val markerOptions = MarkerOptions()
                        .position(venue.location.toLatLng())
                        .title(venue.name)

                    googleMap.addMarker(markerOptions).tag = venue.id
                }
            }

            errorLiveData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), R.string.maps_error, Toast.LENGTH_SHORT).show()
            })

            observeVenues()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.firstOrNull() == PackageManager.PERMISSION_DENIED) {
            showPermissionDialog()
            return
        }

        findUserLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.setOnMarkerClickListener { marker ->
            val venueId = marker.tag as? String

            return@setOnMarkerClickListener if (venueId.isNullOrEmpty()) {
                Toast.makeText(requireContext(), R.string.maps_venue_details_error, Toast.LENGTH_SHORT).show()
                false
            } else {
                replaceFragment(VenueDetailsFragment.create(venueId))
                true
            }
        }

        googleMap.setOnCameraMoveListener {
            val cameraPosition = googleMap.cameraPosition.target
            val viewPortBounds = googleMap.projection.visibleRegion.latLngBounds
            mapsViewModel.search(cameraPosition, viewPortBounds)
        }

        findUserLocation()
    }

    private fun findUserLocation() {
        if (!isLocationGranted(this)) return

        fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
            val userLatLng = location.toLatLng()

            val icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            val markerOptions = MarkerOptions()
                .position(userLatLng)
                .icon(icon)

            googleMap.addMarker(markerOptions)

            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLatLng, VALUE_TO_ZOOM)
            googleMap.moveCamera(cameraUpdate)

            mapsViewModel.search(userLatLng)
        }
    }
}