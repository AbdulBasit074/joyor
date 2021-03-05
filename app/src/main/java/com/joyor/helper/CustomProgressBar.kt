package com.joyor.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.joyor.R
import com.joyor.databinding.ProgressLayoutBinding

class CustomProgressBar(context: Context) : Dialog(context) {


    private lateinit var binding: ProgressLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.progress_layout, null, false)
        this.window!!.decorView.setBackgroundColor(Color.TRANSPARENT)
        setContentView(binding.root)
    }
}