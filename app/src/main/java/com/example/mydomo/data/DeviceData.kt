package com.example.mydomo.data

data class DeviceData(
    val id : String,
    val type : String,
    val availableCommands : ArrayList<String>,
    val opening : Number,
    val power: Number
)
