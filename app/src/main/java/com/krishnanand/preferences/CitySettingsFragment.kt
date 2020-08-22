package com.krishnanand.preferences

import android.os.Bundle
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
        placeViewModel.placesLiveData.observe(requireActivity(), Observer {
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
                        }
                        screen.addPreference(this)
                        addPreference(preference)
                    }
                }
            }
        })
        placeViewModel.fetchPlacesFromJsonFile(requireContext())
    }
}