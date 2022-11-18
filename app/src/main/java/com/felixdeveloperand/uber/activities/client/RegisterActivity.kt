package com.felixdeveloperand.uber.activities.client

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityRegisterBinding
import com.felixdeveloperand.uber.models.Client
import com.felixdeveloperand.uber.provider.AuthProvider
import com.felixdeveloperand.uber.provider.ClientProvider
import com.felixdeveloperand.uber.util.showToast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mPref: SharedPreferences

    private lateinit var mAuthProvider: AuthProvider
    private lateinit var mClientProvider: ClientProvider

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPref = applicationContext.getSharedPreferences("typeUser", MODE_PRIVATE)

        mAuthProvider = AuthProvider()
        mClientProvider = ClientProvider()

        binding.btnRegister.setOnClickListener {
            clickRegisterUser()
        }
    }

    private fun clickRegisterUser() {
        val name = binding.textInputName.text.toString()
        val email = binding.textInputEmail.text.toString()
        val pass = binding.textInputPassword.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
            if(pass.length >= 6){
                register(email,pass)
            }else{
                showToast("The password must be more than 6 characters.")
            }
        }else{
            showToast("Input the all fields, please.")
        }
    }

    private fun register(email: String, pass: String) {
        mAuthProvider.register(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val uid:String = FirebaseAuth.getInstance().currentUser!!.uid
                createClient(Client(uid,email,pass))
            }else{
                showToast("Could not register a user. ${task.exception}")
            }
        }
    }

    private fun createClient(client: Client) {
        mClientProvider.createClient(client).addOnCompleteListener {
            if (it.isSuccessful){
                showToast("The register have been successful")
            }else{
                showToast("Could not create a client")
            }
        }
    }
}