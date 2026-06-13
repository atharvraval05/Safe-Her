package com.example.data.model

data class SafetyFacility(
    val name: String,
    val type: String,
    val distance: String,
    val address: String,
    val phoneNumber: String,
    val relativeLat: Double,
    val relativeLng: Double,
    val specialBadge: String = ""
)
