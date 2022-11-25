package com.felixdeveloperand.uber.models

data class Driver(
    val id:String = "default",
    val username: String? = null,
    val email: String? = null,
    val vehicleBrand:String? = null,
    val vehiclePlate:String? =null
)
