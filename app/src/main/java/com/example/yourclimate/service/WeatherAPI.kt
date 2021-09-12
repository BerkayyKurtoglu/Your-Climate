package com.example.yourclimate.service

import com.example.yourclimate.model.history.HistoryWeather
import com.example.yourclimate.model.nextdays.WeatherModel
import com.example.yourclimate.util.ServiceObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("onecall")
    suspend fun getWeatherWithoutForecast(
        @Query ("units") unit : String = "metric",
        @Query("lat") lat : String = ServiceObject.LAT,
        @Query("lon") lon : String = ServiceObject.LONG,
        @Query("exclude") part : String = "minutely,alerts,daily",
        @Query("appid") key : String = ServiceObject.API_KEY
    ) : Response<WeatherModel>

    @GET("onecall")
    suspend fun getWeatherWithForecast(
        @Query ("units") unit : String = "metric",
        @Query("lat") lat : String = ServiceObject.LAT,
        @Query("lon") lon : String = ServiceObject.LONG,
        @Query("exclude") part : String = "hourly,minutely,alerts,current",
        @Query("appid") key : String = ServiceObject.API_KEY
    ) : Response<WeatherModel>


    @GET("onecall/timemachine")
    suspend fun getYesterdayWeather(
        @Query ("units") unit : String = "metric",
        @Query ("dt") dt : String,
        @Query("lat") lat : String = ServiceObject.LAT,
        @Query("lon") lon : String = ServiceObject.LONG,
        @Query("appid") key : String = ServiceObject.API_KEY
    ) : Response<HistoryWeather>

}