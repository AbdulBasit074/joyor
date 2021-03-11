package com.joyor.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import com.joyor.R

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import java.util.*
import kotlin.collections.ArrayList


fun AppCompatActivity.moveTo(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

fun AppCompatActivity.moveTo(intent: Intent) {
    startActivity(intent)
}

fun AppCompatActivity.moveToAndFinish(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
    finish()
}

fun AppCompatActivity.moveForResult(clazz: Class<*>, requestCode: Int) {
    startActivityForResult(Intent(this, clazz), requestCode)
}

fun Fragment.moveToAndFinish(clazz: Class<*>) {
    startActivity(Intent(requireActivity(), clazz))
    requireActivity().finish()
}

fun Fragment.moveTo(clazz: Class<*>) {
    startActivity(Intent(requireActivity(), clazz))
}

fun Fragment.moveTo(intent: Intent) {
    startActivity(intent)
}

fun Context.customTextView(text: String): TextView {
    val textView = TextView(this)
    textView.setTextColor(getColor(R.color.black))
    textView.text = text
    textView.setPadding(20, 30, 20, 30)
    textView.textSize = 20f
    textView.setTextColor(getColor(R.color.white))
    textView.setBackgroundColor(getColor(R.color.light_reddish))
    return textView
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


/**
 * Enable Gps Location
 * */
fun AppCompatActivity.askToEnableGPS(onActivityResult: (Int, Int, Intent?) -> Unit) {
    val locationRequest = LocationRequest.create()?.apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest!!)
    builder.setAlwaysShow(true)
    val client: SettingsClient = LocationServices.getSettingsClient(this)
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener {
        onActivityResult(Constants.locationDialogRequestKey, Activity.RESULT_OK, null)
    }

    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(
                    this,
                    Constants.locationDialogRequestKey
                )
            } catch (sendEx: IntentSender.SendIntentException) {
                onActivityResult(Constants.locationDialogRequestKey, Activity.RESULT_CANCELED, null)
            }
        }
    }
}

fun AppCompatActivity.getCurrentLocation(fields: ArrayList<Place.Field>, onLocationAvailable: (Place) -> Unit) {
    if (!Places.isInitialized())
        Places.initialize(applicationContext, getString(R.string.google_maps_api), Locale.getDefault())
    val request = FindCurrentPlaceRequest.newInstance(fields)
    val client = Places.createClient(this)
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return
    } else {
        val placeResponse = client.findCurrentPlace(request)
        placeResponse.addOnCompleteListener {
            if (it.isSuccessful) {
                val response = it.result
                var position = 0
                var highestLikelihood = 0.0
                for (i in 0 until response?.placeLikelihoods!!.size) {
                    if (response.placeLikelihoods[i].likelihood > highestLikelihood) {
                        highestLikelihood = response.placeLikelihoods[i].likelihood
                        position = i
                    }
                }
                val place = response.placeLikelihoods[position].place
                onLocationAvailable(place)
            }
        }
    }
}

fun AppCompatActivity.setLanguage(language:String) {
    val displayMetrics = resources.displayMetrics
    val configuration = resources.configuration
    configuration.setLocale(Locale(language))
    resources.updateConfiguration(configuration, displayMetrics)
}
