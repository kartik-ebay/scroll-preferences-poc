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
    val placesLiveData = MutableLiveData<List<Place>>()

    fun fetchPlacesFromJsonFile(context: Context) {
        viewModelScope.launch {
            val places = getJsonDataFromAsset(context, "cities.json")
            placesLiveData.value = places
        }
    }

    suspend fun getJsonDataFromAsset(context: Context, fileName: String): List<Place> = withContext(Dispatchers.IO) {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            return@withContext Gson().fromJson(jsonString, object: TypeToken<List<Place>>() {}.type) as List<Place>
        } catch (ioException: IOException) {
            return@withContext listOf<Place>() as List<Place>
        }
    }
}