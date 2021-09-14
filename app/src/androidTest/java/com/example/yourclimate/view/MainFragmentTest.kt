package com.example.yourclimate.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.yourclimate.doubleRepo.FakeRepositoryForAndroidTest
import com.example.yourclimate.R
import com.example.yourclimate.adapter.MainFragmentTodayReAdapter
import com.example.yourclimate.util.launchFragmentInHiltContainer
import com.example.yourclimate.model.nextdays.Hourly
import com.example.yourclimate.viewmodel.MainFragmentViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltTestRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: WeatherFragmentFactory

    @Inject
    lateinit var mainFragmentTodayReAdapter : MainFragmentTodayReAdapter

    @Inject
    lateinit var hourly: Hourly

    private lateinit var testViewModel : MainFragmentViewModel

    @Before
    fun setup(){
        testViewModel = MainFragmentViewModel(FakeRepositoryForAndroidTest())
        hiltTestRule.inject()
    }

    @Mock
    lateinit var navController: NavController

    @Test
    fun testNavigateFromMainFragmentToForecastFragment(){

        launchFragmentInHiltContainer<MainFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        /*Espresso.onView(ViewMatchers.withId(R.id.nextDaysButton)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(R.id.action_mainFragment_to_forecastFragment)*/

    }


    @Test
    fun testTodayRecyclerView(){

        val list = listOf(hourly)

        launchFragmentInHiltContainer<MainFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
            todayReAdapter.hourlyList = list
        }

        Espresso.onView(withId(R.id.todays_recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MainFragmentTodayReAdapter.ViewHolder>(
                0,ViewActions.click()
            )
        )

        val value = mainFragmentTodayReAdapter.clickedItem

        assertThat(value).isEqualTo(hourly)


    }

}