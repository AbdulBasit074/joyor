package com.joyor.helper


import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView

import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.joyor.R
import com.joyor.model.Product


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


@BindingAdapter("setTextQuantity")
fun setTextQuantity(textView: TextView, product: Product) {
    textView.text = product.quantity.toString()
}

@BindingAdapter("setTotal")
fun setTotal(textView: TextView, model: Product) {
    textView.text = (model.quantity * model.price!!.toInt()).toString()
}


@BindingAdapter("setTextHtml")
fun setTextHtml(textView: TextView, src: String) {
    textView.text = HtmlCompat.fromHtml(src, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

@BindingAdapter("setTextAbout")
fun setTextAbout(textView: TextView, src: String) {
    textView.text = textView.context.getString(R.string.about, src)
}
