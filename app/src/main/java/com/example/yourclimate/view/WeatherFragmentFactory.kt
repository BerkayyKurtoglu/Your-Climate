package com.example.yourclimate.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.yourclimate.adapter.ForecastFragmentReAdapter
import com.example.yourclimate.adapter.MainFragmentTodayReAdapter
import com.example.yourclimate.adapter.MainFragmentYesterdayReAdapter
import com.example.yourclimate.viewmodel.MainFragmentViewModel
import javax.inject.Inject

class WeatherFragmentFactory @Inject constructor(
    private val todayReAdapter: MainFragmentTodayReAdapter,
    private val historicReAdapter: MainFragmentYesterdayReAdapter,
    private val forecastFragmentReAdapter: ForecastFragmentReAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            MainFragment::class.java.name ->{
                MainFragment(todayReAdapter = todayReAdapter,historicReAdapter = historicReAdapter)
            }
            ForecastFragment::class.java.name->{
                ForecastFragment(forecastFragmentReAdapter)
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}