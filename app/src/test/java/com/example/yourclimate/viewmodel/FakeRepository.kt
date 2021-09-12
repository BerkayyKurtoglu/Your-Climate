package com.example.yourclimate.viewmodel

import com.example.yourclimate.model.history.HistoryWeather
import com.example.yourclimate.model.nextdays.WeatherModel
import com.example.yourclimate.repo.WeatherRepoInterface
import com.example.yourclimate.util.Resource

class FakeRepository : WeatherRepoInterface {
    override suspend fun getWeatherInformationWithoutForecast(): Resource<WeatherModel> {
        return Resource.success(null)
    }

    override suspend fun getWeatherInfoWithForecast(): Resource<WeatherModel> {
        return Resource.success(null)
    }

    override suspend fun getHistoricWeather(dt: String): Resource<HistoryWeather> {
        return Resource.success(null)
    }
}