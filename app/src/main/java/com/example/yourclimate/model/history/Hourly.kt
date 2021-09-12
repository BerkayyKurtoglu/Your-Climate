package com.example.yourclimate.model.history

data class Hourly(
    val clouds: Double,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Double,
    val pressure: Double,
    val rain: RainX,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<WeatherX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)