package com.felixdeveloperand.uber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityAuthenticationBinding

class SelectOptionAuthActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAuthenticationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbarcito.toolbar)

        binding.btnGoToLogin.setOnClickListener {

                startActivity(Intent(this@SelectOptionAuthActivity, LoginActivity::class.java).apply {
                    putExtra("key","inteligente")
                })
        }
        binding.btnGoToRegister.setOnClickListener {

                startActivity(Intent(this@SelectOptionAuthActivity, RegisterActivity::class.java).apply {
                    putExtra("key","inteligente")
                })
        }
    }
}