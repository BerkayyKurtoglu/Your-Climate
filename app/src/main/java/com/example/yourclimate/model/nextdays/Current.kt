package com.example.yourclimate.model.nextdays

data class Current(
    val clouds: Double,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Double,
    val pressure: Double,
    val rain: Rain,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<Weather>,
    val wind_deg: Double,
    val wind_speed: Double
)