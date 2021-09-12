package com.example.yourclimate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourclimate.model.nextdays.WeatherModel
import com.example.yourclimate.repo.WeatherRepoInterface
import com.example.yourclimate.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastFragmentViewModel @Inject constructor(
    private val weatherRepo: WeatherRepoInterface
)  : ViewModel() {

    private val _weatherMutableLiveData = MutableLiveData<Resource<WeatherModel>>()
    val weatherLiveData : LiveData<Resource<WeatherModel>> = _weatherMutableLiveData

    init {
        getForecastWeather()
    }

    private fun getForecastWeather(){
        _weatherMutableLiveData.value = Resource.loading(null)
        viewModelScope.launch {
            val response = weatherRepo.getWeatherInfoWithForecast()
            _weatherMutableLiveData.value = response
        }
    }

}