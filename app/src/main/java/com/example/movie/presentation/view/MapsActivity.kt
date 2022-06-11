package com.example.movie.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.R
import com.example.movie.databinding.ActivityMapsBinding
import com.example.movie.domain.entity.Marker
import com.example.movie.domain.entity.MyItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var clusterManager: ClusterManager<MyItem>
    private lateinit var markers: List<Marker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        markers = generateMarkers()
        for(marker in markers){
            val mapMarker = LatLng(marker.lat, marker.lng)
            mMap.addMarker(MarkerOptions().position(mapMarker).title(marker.title))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(marker.lat, marker.lng)))
        }
        val zoomLevel = 11.0f
        val marker = markers[markers.size - 1]
        val latLng = LatLng(marker.lat, marker.lng)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        clusterManager = ClusterManager(this, mMap)
        clusterManager.setAnimation(false)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)


        // Add cluster items (markers) to the cluster manager.
       // addItems()
    }
    private fun addItems() {

        // Set some lat/lng coordinates to start with.
        var lat = 51.5145160
        var lng = -0.1270060

        // Add ten cluster items in close proximity, for purposes of this example.

        for (i in 0..9) {
            val offset = i / 60.0
            lat += offset
            lng += offset
            val offsetItem =
                MyItem(lat, lng, "Title $i", "Snippet $i")
            clusterManager.addItem(offsetItem)
        }
    }

    fun generateMarkers(): List<Marker> {
        val markers: MutableList<Marker> = arrayListOf()
        val marker1 = Marker(
            1,
            43.336636,
            76.952979,
            "Arman 3D"
        )
        markers.add(marker1)
        val marker2 = Marker(
            2,
            43.262158,
            76.941380,
            "Lumiera Cinema"
        )
        markers.add(marker2)
        val marker3 = Marker(
            3,
            43.267937,
            76.934299,
            "Illusion ATRIUM"
        )
        markers.add(marker3)
        val marker4 = Marker(
            4,
            43.244064,
            76.836347,
            "Azia Park"
        )
        markers.add(marker4)
        val marker5 = Marker(
            4,
            43.203103396946645,
            76.8917686805443,
            "MEGA"
        )
        markers.add(marker5)
        val marker6 = Marker(
            4,
            43.20885907739053,
            76.8591530179501,
            "Almaty Mall"
        )
        markers.add(marker6)
        return markers
    }
}