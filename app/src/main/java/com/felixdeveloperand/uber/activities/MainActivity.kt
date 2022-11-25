package com.felixdeveloperand.uber.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.activities.client.MapClientActivity
import com.felixdeveloperand.uber.activities.driver.MapDriverActivity
import com.felixdeveloperand.uber.databinding.ActivityMainBinding
import com.felixdeveloperand.uber.util.showToast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPref = applicationContext.getSharedPreferences("typeUser", MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  mPref.edit()

        binding.btnIamClient.setOnClickListener {
            editor.putString("user", "client")
            editor.apply()
            startActivity(Intent(this, SelectOptionAuthActivity::class.java).apply {
                putExtra("key", "client")
            })
        }
        binding.btnIamDriver.setOnClickListener {
            editor.putString("user", "driver")
            editor.apply()
            startActivity(Intent(this, SelectOptionAuthActivity::class.java).apply {
                putExtra("key", "driver")
            })
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null){
            val user = mPref.getString("user","")
            when (user) {
                "driver" -> {
                    showToast("GO TO DRIVER")

                    Intent(this@MainActivity, MapDriverActivity::class.java).apply {
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                }
                "client" -> {
                    showToast("GO TO CLIENT")

                    Intent(this@MainActivity, MapClientActivity::class.java).apply {
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                }
            }
        }
    }
}