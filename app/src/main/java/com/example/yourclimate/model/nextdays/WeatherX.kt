package com.example.yourclimate.model.nextdays

import javax.inject.Inject

data class WeatherX @Inject constructor(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)