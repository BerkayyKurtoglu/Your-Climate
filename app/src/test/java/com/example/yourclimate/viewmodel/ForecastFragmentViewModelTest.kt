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
class ForecastFragmentViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ForecastFragmentViewModel

    @Before
    fun setup(){
        viewModel = ForecastFragmentViewModel(FakeRepository())
    }

    @Test
    fun `test init, expecting null`(){
        val data = viewModel.weatherLiveData.getOrAwaitValue().data
        assertThat(data).isEqualTo(null)
    }

}