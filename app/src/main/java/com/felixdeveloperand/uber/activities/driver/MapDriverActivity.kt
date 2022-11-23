package com.felixdeveloperand.uber.activities.driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.R
import com.felixdeveloperand.uber.databinding.ActivityMapDriverBinding

class MapDriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapDriverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}