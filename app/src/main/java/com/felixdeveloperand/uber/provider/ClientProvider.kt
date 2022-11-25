package com.felixdeveloperand.uber.provider

import com.felixdeveloperand.uber.models.Client
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ClientProvider {

    private var mDatabase: DatabaseReference = Firebase.database.reference
        .child("Users").child("Clients")

    fun createClient(client: Client): Task<Void> {
        return mDatabase.child(client.id).setValue(client)
    }
}