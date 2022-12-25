package com.felixdeveloperand.uber.activities.client

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.felixdeveloperand.uber.databinding.ActivityMapClientBinding
import com.felixdeveloperand.uber.service.LocationDTO
import com.felixdeveloperand.uber.util.Constants
import com.felixdeveloperand.uber.util.showToast
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit


class MapClientActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding:ActivityMapClientBinding

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

    val LOCATION_REQUEST_CODE = 1

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: LocationUpdateEvent?) {
    //handle updates here
//        event.getLocation().latitude
        val latitud = event?.getLocation()?.latitude
        val longitud = event?.getLocation()?.longitude


        Log.d("location_felix_main", "$latitud and $longitud")
    }

    override fun onStart() {
        super.onStart()
        Log.d("location_felix_main", "EVENT BUS REGISTRADO")
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        Log.d("location_felix_main", "EVENT BUS UNREGISTRADO")
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onMessageEvent(LocationUpdateEvent(LocationDTO()))

        binding.btnStartLocation.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MapClientActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }else{
                showToast("HAY AMO SE ACABO PREFIERO ")
//                startLocationService()
            }
        }

        binding.btnStopLocation.setOnClickListener {
//            stopLocationService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.isNotEmpty()){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                startLocationService()
            }else{
                showToast("Permission denied.")
            }
        }
    }

    private fun isPermissionAccessCoarseLocationApproved():Boolean{
        val permissionAccessCoarseLocationApproved = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED

        if (permissionAccessCoarseLocationApproved) {
//            startService()
        } else {
            // Make a request for foreground-only location access.
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                Constants.LOCATION_SERVICE_REQUEST_TAG
            )
        }
        return permissionAccessCoarseLocationApproved
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)

            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val favoritePlace = LatLng(28.044195,-16.5363842)

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
