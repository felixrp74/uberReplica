package com.felixdeveloperand.uber.activities.client

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.felixdeveloperand.uber.databinding.ActivityMapClientBinding
import com.felixdeveloperand.uber.service.LocationDTO
import com.felixdeveloperand.uber.service.LocationUpdateService
import com.felixdeveloperand.uber.util.Constants
import com.felixdeveloperand.uber.util.showToast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MapClientActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding:ActivityMapClientBinding
    private var latitud:Double = 28.044195
    private var longitud:Double = -16.5363842
    private var favoritePlace = LatLng(28.044195, -16.5363842 )

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: LocationUpdateEvent?) {
        //handle updates here
        latitud = event?.getLocation()?.latitude?: 12.044195
        longitud = event?.getLocation()?.longitude ?: -16.5363842
        Log.d("location_felix_main", "ON MESSAGE EVENT: $latitud and $longitud")

        map.addMarker(MarkerOptions().position(LatLng(latitud, longitud)))

    /*

        val latLng = LatLng(latitud, longitud)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        map.clear()
        markerOptions.title("${latitud.toString()} and ${longitud.toString()}")
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        markerOptions.position
        map.addMarker(markerOptions)
*/

    }

    override fun onStart() {
        super.onStart()
        Log.d("location_felix_main", "ON START")
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        Log.d("location_felix_main", "ON STOP")
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createMapFragment()
        Log.d("location_felix_main", "ON CREATE")

        binding.btnStartLocation.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MapClientActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Constants.SERVICE_LOCATION_REQUEST_CODE
                )
                showToast("PERMISO GARANTIZADO ")
            }else{
                startService(Intent(applicationContext, LocationUpdateService::class.java))
                showToast("HAY AMO SE ACABO PREFIERO ")
            }
        }

        binding.btnStopLocation.setOnClickListener {
            stopService(Intent(applicationContext, LocationUpdateService::class.java))
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.SERVICE_LOCATION_REQUEST_CODE && grantResults.isNotEmpty()){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                startLocationService()
            }else{
                showToast("Permission denied.")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("location_felix_main", "ON MAP READY")
        map = googleMap
        onMessageEvent(LocationUpdateEvent(LocationDTO()))

    }
    private fun createMapFragment() {
        Log.d("location_felix_main", "CREATE MAP FRAGMENT")
        val mapFragment = supportFragmentManager.findFragmentById(com.felixdeveloperand.uber.R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun createMarker(latLng: LatLng) {
        Log.d("location_felix_main", "CREATE MARKER")
        map.addMarker(MarkerOptions().position(latLng))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 12f),
            4000,
            null
        )
    }
}

class LocationUpdateEvent(locationUpdate: LocationDTO) {
    private var location: LocationDTO

    init {
        location = locationUpdate
    }

    fun getLocation(): LocationDTO {
        return location
    }

    fun setLocation(location: LocationDTO) {
        this.location = location
    }
}
