package com.joyor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityGpsBinding
import com.joyor.viewmodel.GpsViewModel

class GpsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGpsBinding
    private lateinit var viewModel: GpsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gps)
        viewModel = ViewModelProviders.of(this).get(GpsViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.isStartClick.observe(this, Observer { it ->
            if (it) {
            }
        })
        viewModel.isBack.observe(this, Observer {
            finish()
        })
    }
}