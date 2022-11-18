package com.felixdeveloperand.uber.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.activities.client.RegisterActivity
import com.felixdeveloperand.uber.activities.driver.PruebaActivity
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

        //setSupportActionBar(binding.toolbarcito.toolbar)
        goToRegister()
        goToLogin()

    }

    private fun goToRegister() {
        binding.btnGoToRegister.setOnClickListener {

            val decision: String = mPref.getString("user", "").toString()
            showToast(decision)

            when (decision) {
                "driver" -> {
                    showToast("DRIVER")
                    val intento = Intent(this@SelectOptionAuthActivity, PruebaActivity::class.java)
                    startActivity(intento)
/*                    startActivity(Intent(
                        this,
                        PruebaActivity::class.java
                    ).apply {
                        putExtra("key", "inteligente")
                    })

 */
                }
                "client" -> {
                    showToast("CLIENT")
                    startActivity(Intent(
                        this@SelectOptionAuthActivity,
                        RegisterActivity::class.java
                    ).apply {
                        putExtra("key", "inteligente")
                    })
                }
            }

//            if (decision == "driver") {
//
//                showToast("DRIVER")
//
//
//
//            } else if (decision == "client") {
//                showToast("CLIENT")
//                startActivity(Intent(
//                    this@SelectOptionAuthActivity,
//                    RegisterActivity::class.java
//                ).apply {
//                    putExtra("key", "inteligente")
//                })
//
//            } else {
//                showToast("No driver No client. SelectOptionActivity.kt")
//            }

        }
    }

    private fun goToLogin() {
        binding.btnGoToLogin.setOnClickListener {

            startActivity(Intent(this@SelectOptionAuthActivity, LoginActivity::class.java).apply {
                putExtra("key", "inteligente")
            })
        }
    }
}