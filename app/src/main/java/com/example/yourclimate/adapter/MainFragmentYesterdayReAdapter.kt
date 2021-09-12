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
import com.example.yourclimate.model.history.Hourly
import com.example.yourclimate.util.getImageFromUrl
import com.example.yourclimate.util.makeCircularProgressDrawable
import java.text.SimpleDateFormat
import javax.inject.Inject

class MainFragmentYesterdayReAdapter @Inject constructor(

) : RecyclerView.Adapter<MainFragmentYesterdayReAdapter.ViewHolder>() {


    val diffUtilCallback = object : DiffUtil.ItemCallback<Hourly>(){
        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncList = AsyncListDiffer(this,diffUtilCallback)

    var hourlyList : List<Hourly>
        get() = asyncList.currentList
        set(value) = asyncList.submitList(value)


    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_item,parent,false)
        return ViewHolder(inflater)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourText = holder.view.findViewById<TextView>(R.id.main_hourText)
        val imageView = holder.view.findViewById<ImageView>(R.id.today_imageView)
        val tempText = holder.view.findViewById<TextView>(R.id.main_tempText)

        val unix = hourlyList[position].dt
        val format = "dd MMMM yyyy, HH:mm:ss"
        val dateFormat = SimpleDateFormat("HH:mm")

        hourText.text = dateFormat.format(unix*1000L)

        val code = hourlyList[position].weather[0].icon
        val imageUrl = "https://openweathermap.org/img/wn/${code}@2x.png"
        imageView.getImageFromUrl(imageUrl, makeCircularProgressDrawable(imageView.context))
        tempText.text ="${hourlyList[position].temp.toInt()}°"

    }

    override fun getItemCount(): Int {
        return hourlyList.size
    }


}