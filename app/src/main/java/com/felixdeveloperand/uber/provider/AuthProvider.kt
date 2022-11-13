package com.felixdeveloperand.uber.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProvider {

    private var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email:String, pass:String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email,pass)
    }
    fun login(email:String, pass:String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email,pass)
    }


}