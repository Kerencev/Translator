package com.kerencev.translator.presentation.base

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kerencev.translator.R

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.icon_image)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .into(this)
}