package com.joyor.helper


import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("setImage")
fun setImage(imageView: ImageView, draw: Drawable) {
    Glide.with(imageView.context).load(draw).into(imageView)
}

@BindingAdapter("setImageUri")
fun setImageUri(imageView: ImageView, src: String) {
    Glide.with(imageView.context).load(src).into(imageView)
}

@BindingAdapter("setColor")
fun setColor(textView: TextView, src: String) {
    textView.setBackgroundColor(Color.parseColor(src))
}

@BindingAdapter("setTextHtml")
fun setTextHtml(textView: TextView, src: String) {
    textView.text = HtmlCompat.fromHtml(src, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

