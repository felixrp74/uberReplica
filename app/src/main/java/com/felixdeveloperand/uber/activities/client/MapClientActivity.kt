package com.felixdeveloperand.uber.activities.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityMapClientBinding

class MapClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}