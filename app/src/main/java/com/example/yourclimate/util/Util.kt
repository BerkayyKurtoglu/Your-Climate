package com.example.yourclimate.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.yourclimate.R
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun ImageView.getImageFromUrl(url : String, circularProgressDrawable: CircularProgressDrawable ){
    this.load(url){
        this.crossfade(true)
        this.crossfade(500)
        this.error(R.drawable.ic_baseline_terrain_24)
        this.placeholder(circularProgressDrawable)
    }
}

fun makeCircularProgressDrawable(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        start()
    }
}

