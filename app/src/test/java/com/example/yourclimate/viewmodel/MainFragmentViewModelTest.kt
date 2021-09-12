package com.example.yourclimate.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.yourclimate.MainCoroutineRule
import com.example.yourclimate.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainFragmentViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainFragmentViewModel

    @Before
    fun setup(){
        viewModel = MainFragmentViewModel(FakeRepository())
    }

    @Test
    fun `test getWholeWeather function, expecting all live data will be null`(){
        viewModel.getWholeWeather("")
        val historyWeather = viewModel.historicLiveData.getOrAwaitValue().data
        val weather = viewModel.weatherLiveData.getOrAwaitValue().data
        assertThat(historyWeather).isEqualTo(null)
        assertThat(weather).isEqualTo(null)
    }

    @Test
    fun `test swipeRefresh function, expecting all live data will be null`(){
        viewModel.swipeRefreshListener("")
        val historyWeather = viewModel.historicLiveData.getOrAwaitValue().data
        val weather = viewModel.weatherLiveData.getOrAwaitValue().data
        assertThat(historyWeather).isEqualTo(null)
        assertThat(weather).isEqualTo(null)
    }

}