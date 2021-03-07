package com.fneis.myevents.extension

import java.text.SimpleDateFormat
import java.util.Date

fun Int.toDateString(): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val netDate = Date(this.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        "Data não informada"
    }
}

