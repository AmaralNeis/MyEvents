package com.fneis.myevents.extension

import android.widget.ImageView
import com.fneis.myevents.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadWith(url: String?, onSuccess: () -> Unit, onError: () -> Unit) {

    url?.let { url ->
        Picasso.get().load(url)
            .fit()
            .placeholder(R.drawable.progress_circle)
            .centerCrop()
            .into(
                this,
                object : Callback {
                    override fun onSuccess() {
                        onSuccess()
                    }

                    override fun onError(e: Exception?) {
                        onError()
                    }
                }
            )
    } ?: kotlin.run {
        onError()
    }
}
