package com.felixdeveloperand.uber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIamClient.setOnClickListener {
            val intent = Intent(this@MainActivity, SelectOptionAuthActivity::class.java)
            startActivity(intent)
        }
        val db = FirebaseFirestore.getInstance()
    }
}