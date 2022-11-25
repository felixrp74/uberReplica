package com.felixdeveloperand.uber.activities.driver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.activities.MainActivity
import com.felixdeveloperand.uber.databinding.ActivityMapDriverBinding
import com.felixdeveloperand.uber.provider.AuthProvider

class MapDriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapDriverBinding
    private lateinit var mAuthProvider: AuthProvider

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
}