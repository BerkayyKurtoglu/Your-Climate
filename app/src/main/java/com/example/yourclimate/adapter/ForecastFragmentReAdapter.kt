package com.example.yourclimate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.yourclimate.R
import com.example.yourclimate.model.nextdays.Daily
import com.example.yourclimate.util.getImageFromUrl
import com.example.yourclimate.util.makeCircularProgressDrawable
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ForecastFragmentReAdapter @Inject constructor(

) : RecyclerView.Adapter<ForecastFragmentReAdapter.ViewHolder>() {

    var clickTest : Daily? = null

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Daily>(){
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this,diffUtilCallback)
    var dailyList : List<Daily>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_fragment_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val iconImageView = holder.view.findViewById<ImageView>(R.id.icon_ImageView)
        val dayText = holder.view.findViewById<TextView>(R.id.day_Text)
        val temp1Text = holder.view.findViewById<TextView>(R.id.temp1_Text)
        val temp2Text = holder.view.findViewById<TextView>(R.id.temp2_Text)
        val rainText = holder.view.findViewById<TextView>(R.id.rain_Text)
        val humidityText = holder.view.findViewById<TextView>(R.id.humidity_Text)

        val day = dailyList[position+2]
        val unix = day.dt
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        dayText.text = dateFormat.format(unix * 1000L)

        val imageCode = day.weather[0].icon
        val imageUrl = "https://openweathermap.org/img/wn/${imageCode}@2x.png"

        iconImageView.getImageFromUrl(imageUrl, makeCircularProgressDrawable(iconImageView.context))
        temp1Text.text ="${day.temp.day}°"
        temp2Text.text = "${day.temp.min}°"
        rainText.text = "${day.rain.toInt()}mm"
        humidityText.text = "${day.humidity.toInt()}%"

        holder.view.setOnClickListener {
            clickTest = dailyList[position]
        }


    }

    override fun getItemCount(): Int {
        return dailyList.size - 2
    }
}