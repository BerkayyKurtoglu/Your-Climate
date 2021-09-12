package com.example.yourclimate.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.yourclimate.databinding.ActivityMotherScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MotherScreen : AppCompatActivity() {

    private var activityMotherScreenBinding : ActivityMotherScreenBinding? = null

    @Inject
    lateinit var weatherFragmentFactory: WeatherFragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = weatherFragmentFactory
        val binding = ActivityMotherScreenBinding.inflate(layoutInflater)
        activityMotherScreenBinding = binding
        setContentView(binding.root)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.statusBarColor = getColor(android.R.color.transparent)

    }

    override fun onDestroy() {
        activityMotherScreenBinding = null
        super.onDestroy()
    }

}