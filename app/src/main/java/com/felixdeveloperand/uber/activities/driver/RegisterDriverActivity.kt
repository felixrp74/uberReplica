package com.felixdeveloperand.uber.activities.driver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityRegisterDriverBinding
import com.felixdeveloperand.uber.models.Driver
import com.felixdeveloperand.uber.provider.AuthProvider
import com.felixdeveloperand.uber.provider.DriverProvider
import com.felixdeveloperand.uber.util.showToast
import com.google.firebase.auth.FirebaseAuth

class RegisterDriverActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterDriverBinding


    private lateinit var mAuthProvider: AuthProvider
    private lateinit var mDriverProvider: DriverProvider

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        mAuthProvider = AuthProvider()
        mDriverProvider = DriverProvider()

        binding.btnRegister.setOnClickListener {
            clickRegisterUser()
        }

 */
    }

    private fun clickRegisterUser() {
        val name = binding.textInputName.text.toString()
        val email = binding.textInputEmail.text.toString()
        val vehicleBrand = binding.textInputVehicleBrand.text.toString()
        val vehiclePlate = binding.textInputVehiclePlate.text.toString()
        val pass = binding.textInputPassword.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && vehicleBrand.isNotEmpty()
            && vehiclePlate.isNotEmpty()){
            if(pass.length >= 6){
                register(name, email, pass, vehicleBrand, vehiclePlate)
            }else{
                showToast("The password must be more than 6 characters.")
            }
        }else{
            showToast("Input the all fields, please.")
        }
    }

    private fun register(
        name: String,
        email: String,
        pass: String,
        vehicleBrand: String,
        vehiclePlate: String
    ) {
        mAuthProvider.register(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val uid:String = FirebaseAuth.getInstance().currentUser!!.uid
                createDriver(Driver(uid,name,email,vehicleBrand, vehiclePlate))
            }else{
                showToast("Could not register a user. ${task.exception}")
            }
        }
    }

    private fun createDriver(driver: Driver) {
        mDriverProvider.createDriver(driver).addOnCompleteListener {
            if (it.isSuccessful){
                showToast("The register have been successful")
            }else{
                showToast("Could not create a client")
            }
        }
    }
}