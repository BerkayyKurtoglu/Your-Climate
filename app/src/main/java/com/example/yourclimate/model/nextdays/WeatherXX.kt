package com.example.yourclimate.model.nextdays

import javax.inject.Inject

data class WeatherXX @Inject constructor(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)