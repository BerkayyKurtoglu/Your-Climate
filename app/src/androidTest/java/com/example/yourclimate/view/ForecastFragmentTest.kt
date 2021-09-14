package com.example.yourclimate.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.yourclimate.R
import com.example.yourclimate.adapter.ForecastFragmentReAdapter
import com.example.yourclimate.util.launchFragmentInHiltContainer
import com.example.yourclimate.model.nextdays.Daily
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ForecastFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltTestRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltTestRule.inject()
    }

    @Inject lateinit var fragmentFactory: WeatherFragmentFactory

    @Inject lateinit var testDaily: Daily

    var forecastFragmentReAdapterTest : ForecastFragmentReAdapter?=null

    @Test
    fun testForecastRecyclerView(){

        launchFragmentInHiltContainer<ForecastFragment>(factory = fragmentFactory){
            forecastFragmentReAdapterTest = forecastFragmentReAdapter
            forecastFragmentReAdapter.dailyList = listOf(testDaily)
        }


        Espresso.onView(ViewMatchers.withId(R.id.forecastRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ForecastFragmentReAdapter.ViewHolder>(
                0,ViewActions.click()
            )
        )

        forecastFragmentReAdapterTest?.let {
            assertThat(forecastFragmentReAdapterTest!!.clickTest).isEqualTo(testDaily)
        }

    }
}