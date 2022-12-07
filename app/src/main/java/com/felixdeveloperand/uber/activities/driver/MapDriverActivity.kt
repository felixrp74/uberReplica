package com.felixdeveloperand.uber.activities.driver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.felixdeveloperand.uber.activities.MainActivity
import com.felixdeveloperand.uber.databinding.ActivityMapDriverBinding
import com.felixdeveloperand.uber.provider.AuthProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapDriverActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapDriverBinding
    private lateinit var mAuthProvider: AuthProvider
    private lateinit var mMap: GoogleMap

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mFusedLocation: FusedLocationProviderClient
    private lateinit var mLocationCallback: LocationCallback
    val LOCATION_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuthProvider = AuthProvider()

        binding.btnLogoutDriver.setOnClickListener {

            mAuthProvider.logout()

            Intent(this@MapDriverActivity, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                for (location in locationResult.locations){
                    // Update UI with location data
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        CameraPosition.builder()
                            .target(LatLng(location.latitude,location.longitude))
                            .zoom(15.0f)
                            .build()
                    ))
                }
            }
        }

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this@MapDriverActivity)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true

        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(300)
            .setFastestInterval(300)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setSmallestDisplacement(5.0f)

        startLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    Looper.myLooper()?.let {
                        mFusedLocation.requestLocationUpdates(
                            mLocationRequest,
                            mLocationCallback,
                            it
                        )
                    }
                }
            }
        }
    }

    private fun startLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Looper.myLooper()?.let {
                mFusedLocation.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    it
                )
            }
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
                            this@MapDriverActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE
                        )

                    }
                    .create()
                    .show()
            }else{
                ActivityCompat.requestPermissions(this@MapDriverActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        }
    }

}