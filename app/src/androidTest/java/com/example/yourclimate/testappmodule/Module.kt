package com.example.yourclimate.testappmodule

import androidx.annotation.ChecksSdkIntAtLeast
import com.example.yourclimate.model.nextdays.WeatherX
import com.example.yourclimate.model.nextdays.WeatherXX
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun injectDouble(): Double = 0.0

    @Singleton
    @Provides
    fun injectInt() : Int = 0

    @Singleton
    @Provides
    fun injectString() : String = ""


    @Singleton
    @Provides
    fun injectList(weatherXX: WeatherXX) : List<WeatherXX> = listOf(weatherXX)

    @ChecksSdkIntAtLeast
    @Provides
    fun injectListWeatherX (weatherX: WeatherX) : List<WeatherX> = listOf(weatherX)

}