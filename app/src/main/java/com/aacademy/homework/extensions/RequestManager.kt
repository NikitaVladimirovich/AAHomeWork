package com.aacademy.homework.extensions

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun RequestManager.loadImage(url: String): RequestBuilder<Drawable> {
    return load(url).transition(DrawableTransitionOptions.withCrossFade())
}
