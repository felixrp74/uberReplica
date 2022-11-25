package com.felixdeveloperand.uber.provider

import com.felixdeveloperand.uber.models.Driver
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DriverProvider {

    private var mDatabase: DatabaseReference = Firebase.database.reference
        .child("Users").child("Drivers")

    fun createDriver(driver: Driver): Task<Void> {
        return mDatabase.child(driver.id).setValue(driver)
    }
}