package com.felixdeveloperand.uber.activities.client

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.telecom.TelecomManager.EXTRA_LOCATION
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.felixdeveloperand.uber.databinding.ActivityMapClientBinding
import com.felixdeveloperand.uber.provider.AuthProvider
import com.felixdeveloperand.uber.util.showToast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.concurrent.TimeUnit

class MapClientActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapClientBinding
    private lateinit var mAuthProvider: AuthProvider
    private lateinit var mMap: GoogleMap

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

    val LOCATION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuthProvider = AuthProvider()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this@MapClientActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MapClientActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this@MapClientActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(this@MapClientActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-0.19, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
/*
        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000)
            .setFastestInterval(1000)
            .setSmallestDisplacement(5.0f)
 */

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)

            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation
/*
                val intent = Intent(ACCESSFINE)
                intent.putExtra(EXTRA_LOCATION, currentLocation)
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
*/
                // Updates notification content if this service is running as a foreground
                // service.
                /*if (serviceRunningInForeground) {
                    notificationManager.notify(
                        NOTIFICATION_ID,
                        generateNotification(currentLocation))
                }*/
            }
        }

        startLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (LOCATION_REQUEST_CODE) {

            1 -> {
                if (ContextCompat.checkSelfPermission(this@MapClientActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    showToast("permission granted")
//                    Looper.myLooper()?.let {
//                        mFusedLocation.requestLocationUpdates(
//                            mLocationRequest,
//                            mLocationCallback,
//                            it
//                        )
//                    }
                }
            }

        }
    }

    private fun startLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            /*Looper.myLooper()?.let {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    it
                )
            }

             */
        }else{
            checkLocationPermissions()
        }
    }

    private fun checkLocationPermissions(){
        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder(this)
                    .setTitle("Proporciona los permisos para continuar")
                    .setMessage("Esta app requiere permisos de ubicacion")
                    .setPositiveButton("OK") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            this@MapClientActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE
                        )

                    }
                    .create()
                    .show()
            }else{
                ActivityCompat.requestPermissions(this@MapClientActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        }
    }
}