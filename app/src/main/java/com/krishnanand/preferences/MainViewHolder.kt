package com.krishnanand.preferences

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainViewHolder(val context: Context, itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var cityTextView: TextView
    var continentTextView: TextView

    init {
        cityTextView = itemView.findViewById(R.id.city)
        continentTextView = itemView.findViewById(R.id.continent)
        itemView.setOnClickListener(this)
    }

    fun bind(place: Place) {
        cityTextView.text = place.city
        continentTextView.text = place.continent
        itemView.tag = place.tag
    }

    override fun onClick(v: View?) {
       Toast.makeText(context, "You clicked on ${v?.tag}", Toast.LENGTH_LONG).show()
    }
}