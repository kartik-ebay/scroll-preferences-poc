package com.krishnanand.preferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LandingActivity: AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        setTitle("Main Screen")
        val preferencesSelectionAdapter = PreferencesSelectionAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.preferences_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = preferencesSelectionAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))
    }

    internal class PreferencesSelectionViewHolder(private val context: Context, itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val selectionTextView: TextView

        init {
            selectionTextView = itemView.findViewById(R.id.preference_selection_text)
            itemView.setOnClickListener(this)
        }

        fun bindViewHolder(selection: String) {
            selectionTextView.setText(selection)
        }

        override fun onClick(v: View?) {
            val continent = selectionTextView.text
            val intent = Intent(context, SettingsActivity::class.java).apply {
                putExtra(Constants.SELECTED_PREFERENCE_EXTRA, continent)
            }
            context.startActivity(intent)
        }
    }

    internal class PreferencesSelectionAdapter(private val context: Context): RecyclerView.Adapter<PreferencesSelectionViewHolder>() {
        private val continentPreferences: List<String> =
            mutableListOf("Europe", "Asia", "Africa", "Australia", "North America", "South America");

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PreferencesSelectionViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.continent_section, parent, false)
            return PreferencesSelectionViewHolder(context, view)
        }

        override fun getItemCount(): Int = continentPreferences.size

        override fun onBindViewHolder(holder: PreferencesSelectionViewHolder, position: Int) {
           val preference = continentPreferences[position]

            holder.bindViewHolder(preference)
        }

    }
}