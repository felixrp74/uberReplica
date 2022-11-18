package com.felixdeveloperand.uber.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityMainBinding

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

}