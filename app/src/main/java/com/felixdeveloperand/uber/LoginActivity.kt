package com.felixdeveloperand.uber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.felixdeveloperand.uber.databinding.ActivityLoginBinding
import com.felixdeveloperand.uber.util.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    val TAG = "LOGIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        if(!email.isEmpty() && !pass.isEmpty()){
            if(pass.length >= 6){

                val db = FirebaseFirestore.getInstance()

                binding.pbCircular.visibility = View.VISIBLE
                binding.btnLogin.visibility = View.GONE

                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            //updateUI(user)


                            showToast("Authentication success.")
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)

                            showToast("Authentication failed.")
                            //updateUI(null)
                        }

                        binding.pbCircular.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE
                    }

            }
        }


    }
}