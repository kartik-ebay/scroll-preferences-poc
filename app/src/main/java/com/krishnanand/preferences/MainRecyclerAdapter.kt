package com.krishnanand.preferences

import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerAdapter(val context: Context, val placeViewModel: PlaceViewModel): RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.continent_city_list, parent, false)
        return MainViewHolder(context, view)
    }

    override fun getItemCount(): Int  {
        val places = placeViewModel.placesLiveData.value
        return if (places.isNullOrEmpty()) 0 else places.size
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val places = placeViewModel.placesLiveData.value
        if (places.isNullOrEmpty())
            return
        holder.bind(places.get(position))
    }
}