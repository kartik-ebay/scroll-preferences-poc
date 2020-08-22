package com.krishnanand.preferences

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat

class CitySettingsFragment: PreferenceFragmentCompat() {
    lateinit var placeViewModel: PlaceViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)
        preferenceScreen = screen
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        val fragmentActivity = requireActivity()
        placeViewModel.placesLiveData.observe(fragmentActivity, Observer {
            val screen = this.preferenceScreen
            for ((continent, places) in it) {
                val preferenceKey = continent.replace(' ', '_')
                PreferenceCategory(context).apply {
                    key = preferenceKey
                    title = continent
                    for (place in places) {
                        val preference = Preference(context).apply {
                            key = place.city.replace(' ', '_')
                            title = place.city
                            summary = place.country
                            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                                Toast.makeText(context, "${it.title} preference was clicked", Toast.LENGTH_SHORT).show()
                                true
                            }
                        }
                        screen.addPreference(this)
                        addPreference(preference)
                    }
                }
            }
            val handler = Handler(fragmentActivity.mainLooper)
            val selectionExtra = fragmentActivity.intent?.getStringExtra(Constants.SELECTED_PREFERENCE_EXTRA)

            handler.post {
                val preferenceKey = selectionExtra?.replace(' ', '_')
                preferenceKey?.let {
                    val preference = findPreference<Preference>(it)
                    preference?.let {
                        scrollToPreference(preference)
                    }
                }
            }
        })
        placeViewModel.fetchPlacesFromJsonFile(requireContext())
    }
}