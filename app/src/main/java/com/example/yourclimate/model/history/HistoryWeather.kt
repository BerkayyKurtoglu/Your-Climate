package com.example.yourclimate.model.history

data class HistoryWeather(
    val current: Current,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)