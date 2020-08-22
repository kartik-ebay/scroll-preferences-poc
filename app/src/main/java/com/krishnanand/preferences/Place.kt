package com.krishnanand.preferences

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(val city: String, val continent:String, val tag: String, val country: String): Parcelable