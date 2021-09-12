package com.example.yourclimate.model.nextdays


data class Daily(
    val clouds: Double,
    val dew_point: Double,
    val dt: Int,
    val feels_like: FeelsLike,
    val humidity: Double,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Double,
    val rain: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather:  List<WeatherX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)