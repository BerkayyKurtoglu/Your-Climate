package com.example.yourclimate.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.yourclimate.R
import com.example.yourclimate.adapter.MainFragmentTodayReAdapter
import com.example.yourclimate.adapter.MainFragmentYesterdayReAdapter
import com.example.yourclimate.databinding.MainFragmentBinding
import com.example.yourclimate.util.Status
import com.example.yourclimate.viewmodel.MainFragmentViewModel
import com.google.android.material.transition.platform.MaterialSharedAxis
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainFragment @Inject constructor(
    val todayReAdapter: MainFragmentTodayReAdapter,
    val historicReAdapter : MainFragmentYesterdayReAdapter
) : Fragment(R.layout.main_fragment) {

    private lateinit var viewModel : MainFragmentViewModel
    private var mainFragmentBinding: MainFragmentBinding? = null
    private var dt : Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MainFragmentBinding.bind(view)
        mainFragmentBinding = binding

        viewModel = ViewModelProvider(requireActivity()).get(MainFragmentViewModel::class.java)
        val date = Calendar.getInstance()
        date.add(Calendar.DATE,-1)
        dt =  date.timeInMillis/1000
        viewModel.getWholeWeather(dt.toString())

        binding.nextDaysButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_forecastFragment)
        }
        binding.todaysRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            this.adapter = todayReAdapter
        }
        binding.yesterdayRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            this.adapter = historicReAdapter
        }
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
            viewModel.swipeRefreshListener()
        }
        binding.yesterdayButton.setOnClickListener {
            yesterdayClicked(it)
        }
        binding.todayButton.setOnClickListener {
            todayClicked(it)
        }

        observeViewModel()
    }

    private fun yesterdayClicked(view : View){
        mainFragmentBinding?.let {
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, false)
            TransitionManager.beginDelayedTransition(it.root as ViewGroup,sharedAxis)

            it.yesterdayRecyclerView.visibility = View.VISIBLE
            it.todaysRecyclerView.visibility = View.GONE
            it.todayImageView.visibility = View.GONE
            it.yesterdayImageView.visibility = View.VISIBLE
            it.yesterdayTempText.visibility = View.VISIBLE
            it.todayTempText.visibility = View.GONE
            it.yesterdayWeatherText.visibility = View.VISIBLE
            it.todayWeatherText.visibility = View.GONE
            it.yesterdayDateText.visibility = View.VISIBLE
            it.todayDateText.visibility = View.GONE
            it.yesterdayIndicator.visibility = View.VISIBLE
            it.todayIndicator.visibility = View.GONE
        }
    }

    private fun todayClicked(view : View){
        mainFragmentBinding?.let {
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.X, true)
            TransitionManager.beginDelayedTransition(it.root as ViewGroup,sharedAxis)

            it.yesterdayRecyclerView.visibility = View.GONE
            it.todaysRecyclerView.visibility = View.VISIBLE
            it.todayImageView.visibility = View.VISIBLE
            it.yesterdayImageView.visibility = View.GONE
            it.yesterdayTempText.visibility = View.GONE
            it.todayTempText.visibility = View.VISIBLE
            it.yesterdayWeatherText.visibility = View.GONE
            it.todayWeatherText.visibility = View.VISIBLE
            it.yesterdayDateText.visibility = View.GONE
            it.todayDateText.visibility = View.VISIBLE
            it.yesterdayIndicator.visibility = View.GONE
            it.todayIndicator.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun observeViewModel(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    mainFragmentBinding?.let {
                        it.lottieAnimationView.visibility = View.INVISIBLE
                        it.lottieAnimationView.cancelAnimation()
                        it.wholeScreen.visibility = View.VISIBLE
                    }
                    it.data?.let {weatherModel->
                        val unix = weatherModel.current.dt
                        val format = SimpleDateFormat("dd/MM/yyyy")
                        mainFragmentBinding?.let {
                            it.todayWeatherText.text = weatherModel.current.weather[0].main
                            when(weatherModel.current.weather[0].main){
                                "Thunderstorm" ->{
                                    it.todayImageView.load(R.drawable.windy){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                "Rain" ->{
                                    it.todayImageView.load(R.drawable.rain){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                "Clear" ->{
                                    it.todayImageView.load(R.drawable.sun){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                "Snow" ->{
                                    it.todayImageView.load(R.drawable.snow){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                else ->{
                                    it.todayImageView.load(R.drawable.other){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                            }
                            it.todayDateText.text = format.format(unix * 1000L)
                            it.placeText.text = weatherModel.timezone
                            it.todayTempText.text ="${weatherModel.current.temp.toInt()}°"
                        }
                        todayReAdapter.hourlyList = weatherModel.hourly
                    } ?: println("Success but not working")
                }
                Status.LOADING ->{
                    mainFragmentBinding?.let {
                        it.lottieAnimationView.visibility = View.VISIBLE
                        it.lottieAnimationView.playAnimation()
                        it.wholeScreen.visibility = View.INVISIBLE
                    }
                    println("Loading")
                }
                Status.ERROR ->{
                    mainFragmentBinding?.let {
                        it.lottieAnimationView.visibility = View.INVISIBLE
                        it.lottieAnimationView.cancelAnimation()
                        it.wholeScreen.visibility = View.INVISIBLE
                    }
                    Toast.makeText(requireContext(), it.message ?: "There is an error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.historicLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                    it.data?.let {historyWeather->
                        val unix = historyWeather.current.dt
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        mainFragmentBinding?.let {
                            it.yesterdayWeatherText.text = historyWeather.current.weather[0].main
                            it.yesterdayDateText.text = dateFormat.format(unix * 1000L)
                            when(historyWeather.current.weather[0].main){
                                "Thunderstorm" ->{
                                    it.yesterdayImageView.load(R.drawable.windy){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                "Rain" ->{
                                    it.yesterdayImageView.load(R.drawable.rain){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                "Clear" ->{
                                    it.yesterdayImageView.load(R.drawable.sun){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                "Snow" ->{
                                    it.yesterdayImageView.load(R.drawable.snow){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                                else ->{
                                    it.yesterdayImageView.load(R.drawable.other){
                                        this.crossfade(true)
                                        this.crossfade(500)
                                    }
                                }
                            }
                            it.placeText.text = historyWeather.timezone
                            it.yesterdayTempText.text ="${historyWeather.current.temp.toInt()}°"
                        }
                        historicReAdapter.hourlyList = historyWeather.hourly
                    } ?: println("Success but not working")
                }
                Status.LOADING->{
                    mainFragmentBinding?.let {
                        it.lottieAnimationView.visibility = View.VISIBLE
                        it.lottieAnimationView.playAnimation()
                        it.wholeScreen.visibility = View.INVISIBLE
                    }
                }
                Status.ERROR->{
                    mainFragmentBinding?.let {
                        it.lottieAnimationView.visibility = View.INVISIBLE
                        it.lottieAnimationView.cancelAnimation()
                        it.wholeScreen.visibility = View.INVISIBLE
                    }
                    println(it.message ?: "Error !")
                }
            }
        }
    }

    override fun onDestroy() {
        mainFragmentBinding = null
        super.onDestroy()
    }

}