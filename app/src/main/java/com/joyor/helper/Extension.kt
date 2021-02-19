package com.joyor.helper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.moveTo(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

fun AppCompatActivity.moveToAndFinish(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
    finish()
}
fun Context.moveTo(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}