package com.buddman.zepespot

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainMapsActivity : BaseActivity(), OnMapReadyCallback {
    override val viewId: Int = R.layout.activity_main_maps
    override val toolbarId: Int = 0
    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun setDefault() {
        initLocation()
    }

    @SuppressLint("MissingPermission")
    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient.run {
            lastLocation.addOnSuccessListener {
                LatLng(it.latitude, it.longitude).apply {
                    mMap.addMarker(MarkerOptions().position(this).title("나는 여기!"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this, 17.0f))
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(37.5337709, 126.9635608)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
