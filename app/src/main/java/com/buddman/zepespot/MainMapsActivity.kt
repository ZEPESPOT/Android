package com.buddman.zepespot

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.buddman.zepespot.databinding.MapsBottomCardBinding
import com.buddman.zepespot.models.Course
import com.buddman.zepespot.utils.NetworkManager
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main_maps.*
import org.jetbrains.anko.startActivity


class MainMapsActivity : BaseActivity(), OnMapReadyCallback {
    override val viewId: Int = R.layout.activity_main_maps
    override val toolbarId: Int = 0
    private lateinit var mMap: GoogleMap

    private val spotArray : ArrayList<Course> = arrayListOf()
    private val spotAdapter: LastAdapter by lazy { LastAdapter(spotArray, BR.content) }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun setDefault() {
        initLocation()
        initBottom()
        getData()
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

    private fun initBottom() {
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(mainCardRv)
        mainCardRv.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        spotAdapter
                .map<Course, MapsBottomCardBinding>(R.layout.maps_bottom_card) {
                    onClick {
                        val position = it.adapterPosition
                        val lastSelectedPosition = spotArray.indexOf(spotArray.findLast { it.isSelected }!!)
                        spotArray.forEachIndexed { index, spot -> spot.isSelected = (position == index) }
                        spotAdapter.notifyItemChanged(lastSelectedPosition)
                        spotAdapter.notifyItemChanged(position)
                        mainCardRv.scrollToPosition(position)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(spotArray[position].latitude.toDouble(), spotArray[position].longitude.toDouble()), 17.0f)
                        )
                    }

                    onLongClick {
                        startActivity<PhotoEditActivity>()
                    }
                }
                .into(mainCardRv)
    }

    private fun getData(){
        NetworkManager.getCourse(1)
                .subscribeOnIO()
                .onUI {
                    if(it.size > 0) {
                        it.sortBy { it -> it.sequence_number }
                        spotArray.clear()
                        spotArray.addAll(it)
                        spotArray.forEach {
                            mMap.addMarker(MarkerOptions().position(LatLng(it.latitude.toDouble(), it.longitude.toDouble())).title(it.sequence_name)).showInfoWindow()
                        }
                        spotArray[0].isSelected = true
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(spotArray[0].latitude.toDouble(), spotArray[0].longitude.toDouble()), 16.0f)
                        )
                        spotAdapter.notifyItemRangeChanged(0, spotArray.size)
                    }
                }
    }
}