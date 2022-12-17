package com.felixdeveloperand.uber.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.activities.client.RegisterActivity
import com.felixdeveloperand.uber.activities.driver.RegisterDriverActivity
import com.felixdeveloperand.uber.databinding.ActivityAuthenticationBinding
import com.felixdeveloperand.uber.util.showToast

class SelectOptionAuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var mPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPref = applicationContext.getSharedPreferences("typeUser", MODE_PRIVATE)

        goToLogin()
        goToRegister()
    }

    private fun goToLogin() {
        binding.btnGoToLogin.setOnClickListener {
            val decision: String = mPref.getString("user", "").toString()
            showToast("LA VARIABLE DECISION = $decision")

            startActivity(Intent(this@SelectOptionAuthActivity, LoginActivity::class.java).apply {
                putExtra("key", decision)
            })
        }
    }

    private fun goToRegister() {
        binding.btnGoToRegister.setOnClickListener {

            val decision: String = mPref.getString("user", "").toString()
            showToast("LA VARIABLE DECISION = $decision")

            when (decision) {
                "driver" -> {
                    showToast("GO TO DRIVER")
                    startActivity(Intent(
                        this@SelectOptionAuthActivity,
                        RegisterDriverActivity::class.java
                    ))
                }
                "client" -> {
                    showToast("GO TO CLIENT")
                    startActivity(Intent(
                        this@SelectOptionAuthActivity,
                        RegisterActivity::class.java
                    ))
                }
            }
        }
    }

}