package com.fneis.myevents.extension

import java.text.DecimalFormat

fun Double.toPriceAmount(): String {
    val dec = DecimalFormat("R$ ###,###,###.00")
    return dec.format(this)
}

