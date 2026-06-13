package com.example.data.model

data class SafetyDomain(
    val title: String,
    val description: String,
    val quickAction: String,
    val iconType: String // "LEGAL", "PHYSICAL", "DIGITAL", "SOCIAL"
)
