package com.sapient.demoapp.presentation.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sapient.demoapp.R

fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        ).load(it).into(this)
    }
}