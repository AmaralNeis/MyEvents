package com.fneis.myevents.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fneis.myevents.R

fun ImageView.loadWith(url: String?, onSuccess: () -> Unit, onError: () -> Unit) {

    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.progress_circle)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                onError()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                onSuccess()
                return false
            }
        })
        .into(this)
}
