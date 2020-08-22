package com.krishnanand.preferences

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PlaceViewModel(): ViewModel() {
    val placesLiveData = MutableLiveData<Map<String, List<Place>>>()
    val placesData = MutableLiveData<List<Place>>()

    fun fetchPlacesFromJsonFile(context: Context) {
        viewModelScope.launch {
            val places = getJsonDataFromAsset(context, "cities.json")
            placesLiveData.value = places
            val placeList = arrayListOf<Place>()
            for ((continent, placeValues) in places) {
                placeList.addAll(placeValues)
            }
            placesData.value = placeList
        }
    }

    private suspend fun getJsonDataFromAsset(context: Context, fileName: String): Map<String, List<Place>> = withContext(Dispatchers.IO) {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            Gson().fromJson(jsonString, object: TypeToken<Map<String, List<Place>>>() {}.type) as Map<String, List<Place>>
        } catch (ioException: IOException) {
            mapOf<String, List<Place>>()
        }
    }
}