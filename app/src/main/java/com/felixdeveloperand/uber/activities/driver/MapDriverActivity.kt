package com.felixdeveloperand.uber.activities.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.activities.MainActivity
import com.felixdeveloperand.uber.databinding.ActivityMapDriverBinding
import com.felixdeveloperand.uber.provider.AuthProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapDriverActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapDriverBinding
    private lateinit var mAuthProvider: AuthProvider
    private lateinit var mMap: GoogleMap

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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-0.19, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}