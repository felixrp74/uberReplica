package com.felixdeveloperand.uber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.R
import com.felixdeveloperand.uber.databinding.ActivityAuthenticationBinding

class SelectOptionAuthActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarcito.toolbar)

    }
}