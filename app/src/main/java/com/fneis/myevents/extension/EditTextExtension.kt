package com.fneis.myevents.extension

import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

fun EditText.afterTextChanged(value: (String) -> Unit = {}) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { /* Not Caled.*/ }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { /* Not Caled.*/ }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { value(s.toString()) }
        }
    })
}

