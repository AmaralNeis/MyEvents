package com.fneis.myevents.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: Int?,
    val description: String?,
    val image: String?,
    val title: String?,
    val price: Double?,
    val longitude: Double?,
    val latitude: Double?,
    val date: Int
) : Parcelable