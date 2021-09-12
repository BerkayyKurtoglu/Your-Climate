package com.example.yourclimate.repo

import com.example.yourclimate.model.history.HistoryWeather
import com.example.yourclimate.model.nextdays.WeatherModel
import com.example.yourclimate.util.Resource

interface WeatherRepoInterface {

    suspend fun getWeatherInformationWithoutForecast() : Resource<WeatherModel>

    suspend fun getWeatherInfoWithForecast() : Resource<WeatherModel>

    suspend fun getHistoricWeather(dt : String) : Resource<HistoryWeather>

}