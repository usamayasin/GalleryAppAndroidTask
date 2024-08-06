package com.example.avrioctask.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("bind:loadImage")
fun loadImage(view: ImageView, url: Uri?) {
    url?.let {
        Glide.with(view.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(view)
    }
}

@BindingAdapter("bind:name", "bind:count")
fun setNameAndCount(view: TextView, name: String, count:Int) {
    view.text = "$name ( $count )"
}

