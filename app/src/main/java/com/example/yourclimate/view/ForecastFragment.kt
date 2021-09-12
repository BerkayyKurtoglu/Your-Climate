package com.example.yourclimate.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yourclimate.R
import com.example.yourclimate.adapter.ForecastFragmentReAdapter
import com.example.yourclimate.databinding.ForecastFragmentBinding
import com.example.yourclimate.util.Status
import com.example.yourclimate.util.getImageFromUrl
import com.example.yourclimate.util.makeCircularProgressDrawable
import com.example.yourclimate.viewmodel.ForecastFragmentViewModel
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import javax.inject.Inject

class ForecastFragment @Inject constructor(
    val forecastFragmentReAdapter: ForecastFragmentReAdapter
) : Fragment(R.layout.forecast_fragment) {

    private lateinit var viewModel : ForecastFragmentViewModel
    private var forecastFragmentBinding : ForecastFragmentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X,true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ForecastFragmentViewModel::class.java)
        val binding = ForecastFragmentBinding.bind(view)
        forecastFragmentBinding = binding

        binding.forecastRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = forecastFragmentReAdapter
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        subscribeToObservers()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun subscribeToObservers(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner){

            when(it.status){
                Status.SUCCESS ->{
                    it.data?.let {weatherModel->
                        forecastFragmentReAdapter.dailyList = weatherModel.daily
                        val firstDay = weatherModel.daily[1]
                        val imageCode = firstDay.weather[0].icon
                        val unix = firstDay.dt
                        val format = "dd MMMM yyyy, HH:mm:ss"
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        forecastFragmentBinding?.let {
                            it.dayText.text = "Tomorrow"
                            val imageUrl = "https://openweathermap.org/img/wn/${imageCode}@2x.png"
                            it.iconImageView.getImageFromUrl(imageUrl, makeCircularProgressDrawable(requireContext()))
                            it.temp1Text.text = "${firstDay.temp.day}°"
                            it.temp2Text.text = "${firstDay.temp.min}°"
                            it.windText.text = "${firstDay.wind_speed}m/s"
                            it.visibilityText.text = "${firstDay.feels_like.day}°"
                            it.humidityText.text = "${firstDay.humidity.toInt()}%"
                            it.uvText.text = "${firstDay.uvi}"
                        }
                    } ?: println("Success but not working")
                }
                Status.LOADING ->{
                }
                Status.ERROR ->{
                }
            }
        }
    }

    override fun onDestroy() {
        forecastFragmentBinding = null
        super.onDestroy()
    }

}