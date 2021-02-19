package com.joyor.helper


import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("setImage")
fun setImage(imageView: ImageView, draw: Drawable) {
    Glide.with(imageView.context).load(draw).into(imageView)
}

@BindingAdapter("setImageConstraints")
fun setImageConstraints(imageView: ConstraintLayout, draw: Drawable) {
    imageView.background = draw
}
