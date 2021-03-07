package com.fneis.myevents.extension

fun String.isEmail() = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().matches(this)
fun String.isNotEmail() = !isEmail()