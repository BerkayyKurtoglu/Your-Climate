package com.example.yourclimate.model.nextdays

import javax.inject.Inject

data class Hourly @Inject constructor(
    val clouds: Double,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Double,
    val pop: Double,
    val pressure: Double,
    val rain: RainX,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<WeatherXX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)