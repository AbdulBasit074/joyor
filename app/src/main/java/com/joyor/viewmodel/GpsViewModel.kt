package com.joyor.viewmodel

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.joyor.R

class GpsViewModel : ViewModel() {

    var isStartClick: MutableLiveData<Boolean> = MutableLiveData()
    fun onStartClick(view: View) {
        isStartClick.value = true
    }


}