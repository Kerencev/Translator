package com.kerencev.translator.presentation.base

import android.view.View

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}