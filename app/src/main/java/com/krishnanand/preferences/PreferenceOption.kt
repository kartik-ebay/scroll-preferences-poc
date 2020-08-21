package com.krishnanand.preferences

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PreferenceOption(val key: String, val title: String, val summary: String)

@Parcelize
data class Place(val city: String, val continent:String, val tag: String): Parcelable