package com.example.yourclimate.repo

import com.example.yourclimate.model.history.HistoryWeather
import com.example.yourclimate.model.nextdays.WeatherModel
import com.example.yourclimate.service.WeatherAPI
import com.example.yourclimate.util.Resource
import retrofit2.HttpException
import java.lang.Exception
import java.net.HttpRetryException
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val weatherAPI: WeatherAPI
) : WeatherRepoInterface {

    override suspend fun getWeatherInformationWithoutForecast(): Resource<WeatherModel> {
        return try {
            val response = weatherAPI.getWeatherWithoutForecast()
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("Error !",null)
            }else{
                return Resource.error("Error !",null)
            }
        }catch (e : HttpException){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }catch (e : HttpRetryException){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }
    }

    override suspend fun getWeatherInfoWithForecast(): Resource<WeatherModel> {
        return try {
            val response = weatherAPI.getWeatherWithForecast()
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("Error !",null)
            }else{
                return Resource.error("Error !",null)
            }
        }catch (e : HttpException){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }catch (e : HttpRetryException){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }catch (e : Exception){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }
    }

    override suspend fun getHistoricWeather(dt : String): Resource<HistoryWeather> {
        return try {
            val response = weatherAPI.getYesterdayWeather(dt = dt)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("Unexpected problem is occured !",null)
            }else return Resource.error("Unexpected problem is occured !",null)
        }catch (e : HttpException){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }catch (e : HttpRetryException){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }catch (e : Exception){
            return Resource.error(e.localizedMessage ?: "Unexpected problem is occured !",null)
        }
    }
}