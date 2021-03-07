package com.fneis.myevents.extension

fun String.isEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isNotEmail() = !isEmail()