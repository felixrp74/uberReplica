package com.felixdeveloperand.uber.activities.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.activities.MainActivity
import com.felixdeveloperand.uber.activities.driver.MapDriverActivity
import com.felixdeveloperand.uber.databinding.ActivityMapClientBinding
import com.felixdeveloperand.uber.provider.AuthProvider

class MapClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapClientBinding
    private lateinit var mAuthProvider: AuthProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuthProvider = AuthProvider()

        binding.btnLogoutClient.setOnClickListener {
            mAuthProvider.logout()

            Intent(this@MapClientActivity, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

    }
}