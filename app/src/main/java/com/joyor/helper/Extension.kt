package com.joyor.helper

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide


fun AppCompatActivity.moveTo(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

fun AppCompatActivity.moveToAndFinish(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
    finish()
}
fun Fragment.moveToAndFinish(clazz: Class<*>) {
    startActivity(Intent(requireActivity(), clazz))
    requireActivity().finish()
}
fun Fragment.moveTo(clazz: Class<*>) {
    startActivity(Intent(requireActivity(), clazz))
}


fun Context.moveTo(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

@BindingAdapter("setImageSrc")
fun setImageSrc(imageView: ImageView, drawable: Drawable) {
    Glide.with(imageView.context).load(drawable).into(imageView)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
}
