package com.example.mydomo

data class DeviceData(
    val id : String,
    val type : String,
    val availableCommands : ArrayList<String>,
    val value : Number
)
