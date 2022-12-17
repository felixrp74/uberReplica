package com.felixdeveloperand.uber.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.felixdeveloperand.uber.activities.client.MapClientActivity
import com.felixdeveloperand.uber.activities.driver.MapDriverActivity
import com.felixdeveloperand.uber.databinding.ActivityLoginBinding
import com.felixdeveloperand.uber.util.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mPref: SharedPreferences

    val TAG = "LOGIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPref = applicationContext.getSharedPreferences("typeUser", MODE_PRIVATE)

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            login()
        }
    }

    private fun login() {
        val email = binding.textInputEmail.text.toString()
        val pass = binding.textInputPassword.text.toString()

        if(email.isNotEmpty() && pass.isNotEmpty()){
            if(pass.length >= 6){

                binding.pbCircular.visibility = View.VISIBLE
                binding.btnLogin.visibility = View.GONE

                val decision: String = mPref.getString("user", "").toString()
                showToast("LA VARIABLE DECISION = $decision")

                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            when(decision){
                                "driver" ->{
                                    Intent(this@LoginActivity, MapDriverActivity::class.java).apply {
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(this)
                                    }
                                }
                                "client" ->{

                                    Intent(this@LoginActivity, MapClientActivity::class.java).apply {
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(this)
                                    }

                                    showToast("CLIENTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE")
                                }
                            }
                            showToast("Login success.")
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)

                            showToast("Login failed.")
                            //updateUI(null)
                        }

                        binding.pbCircular.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE
                    }
            }
        }
    }
}