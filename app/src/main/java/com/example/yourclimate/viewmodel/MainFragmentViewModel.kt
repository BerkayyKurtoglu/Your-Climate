package com.example.yourclimate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourclimate.model.history.HistoryWeather
import com.example.yourclimate.model.nextdays.WeatherModel
import com.example.yourclimate.repo.WeatherRepoInterface
import com.example.yourclimate.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val weatherRepo : WeatherRepoInterface
) : ViewModel() {

    private val _weatherLiveDate = MutableLiveData<Resource<WeatherModel>>()
    val weatherLiveData : LiveData<Resource<WeatherModel>> = _weatherLiveDate

    private val _historicLiveDate = MutableLiveData<Resource<HistoryWeather>>()
    val historicLiveData : LiveData<Resource<HistoryWeather>> = _historicLiveDate

    fun getWholeWeather(dt : String){
        getNextData()
        getHistoricDate(dt)
    }

    private fun getNextData(){
        _weatherLiveDate.value = Resource.loading(null)
        viewModelScope.launch {
            val response = weatherRepo.getWeatherInformationWithoutForecast()
            _weatherLiveDate.value = response
        }
    }

    private fun getHistoricDate(dt : String){
        _historicLiveDate.value = Resource.loading(null)
        viewModelScope.launch {
            val response = weatherRepo.getHistoricWeather(dt)
            _historicLiveDate.value = response
        }
    }

    fun swipeRefreshListener(dt : String){
        getNextData()
        getHistoricDate(dt)
    }

}