package com.example.yourclimate.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.transition.TransitionManager
import android.view.View
import android.view.WindowManager
import com.example.yourclimate.databinding.ActivityMainBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mainActivityBinding: ActivityMainBinding?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        mainActivityBinding = binding
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        switchToMotherScreen()


    }

    private fun switchToMotherScreen(){
        CoroutineScope(Dispatchers.Default).launch {
            delay(1700)
            withContext(Dispatchers.Main){
                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Y, true)
                sharedAxis.duration = 1700
                TransitionManager.beginDelayedTransition(mainActivityBinding!!.root, sharedAxis)
                mainActivityBinding?.appNameText?.visibility = View.VISIBLE
                mainActivityBinding?.subtitleText?.visibility = View.VISIBLE
            }
            delay(2000)
            withContext(Dispatchers.Main){
                val intent = Intent(this@MainActivity, MotherScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                this@MainActivity.finish()
            }
        }
    }

    override fun onDestroy() {
        mainActivityBinding = null
        super.onDestroy()
    }



}