package com.felixdeveloperand.uber

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityRegisterBinding
import com.felixdeveloperand.uber.util.showToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPref = applicationContext.getSharedPreferences("typeUser", MODE_PRIVATE)

        val selectedUser = mPref.getString("user","")
        showToast(selectedUser.toString())

    }
}