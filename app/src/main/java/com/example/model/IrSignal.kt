package com.example.model

data class IrSignal(
    val commandName: String,
    val hexCode: String,
    val protocol: String = "NEC",
    val frequencyKHz: Int = 38,
    val timestamp: Long = System.currentTimeMillis()
)
