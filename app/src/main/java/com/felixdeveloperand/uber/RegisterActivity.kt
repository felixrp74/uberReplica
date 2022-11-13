package com.felixdeveloperand.uber

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixdeveloperand.uber.databinding.ActivityRegisterBinding
import com.felixdeveloperand.uber.util.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mPref: SharedPreferences
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPref = applicationContext.getSharedPreferences("typeUser", MODE_PRIVATE)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = Firebase.database.reference

        binding.btnRegister.setOnClickListener {
            //writeNewUser("jgur7862", "camilo", "cesto@gmail.com.pe")
            registerUser()

        }

    }

    private fun registerUser() {
        val name = binding.textInputName.text.toString()
        val email = binding.textInputEmail.text.toString()
        val pass = binding.textInputPassword.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
            if(pass.length >= 6){
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        saveUser(name, email)
                    }else{
                        showToast("Could not register a user. ${task.exception}")
                    }
                }
            }else{
                showToast("The password must be more than 6 characters.")
            }
        }else{
            showToast("Input the all fields, please.")
        }
    }

    private fun saveUser(name: String, email: String) {
        val selectedUser = mPref.getString("user","")
        //showToast(selectedUser.toString())
        val user = User(name, email)

        if (selectedUser.equals("driver")){
            mDatabase.child("Users").child("Drivers").push().setValue(user)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        showToast("Successful registered user.")
                    }else{
                        showToast("Failed , llego hasta mDatabase.")
                    }
                }

        }else if(selectedUser.equals("client")){
            mDatabase.child("Users").child("Clients").push().setValue(user)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        showToast("Successful registered user.")
                    }else{
                        showToast("Failed , llego hasta mDatabase.")
                    }
                }
        }
    }
/*
    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)

        mDatabase.child("users").child(userId).setValue(user)
    }

 */


}