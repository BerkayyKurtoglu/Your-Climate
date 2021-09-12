package com.example.yourclimate.dependencyinjection

import com.example.yourclimate.repo.WeatherRepo
import com.example.yourclimate.repo.WeatherRepoInterface
import com.example.yourclimate.service.WeatherAPI
import com.example.yourclimate.util.ServiceObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun injectWeatherApi() : WeatherAPI{
        return Retrofit.Builder()
            .baseUrl(ServiceObject.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectWeatherInterface(weatherAPI: WeatherAPI) : WeatherRepoInterface {
        return WeatherRepo(weatherAPI)
    }


}