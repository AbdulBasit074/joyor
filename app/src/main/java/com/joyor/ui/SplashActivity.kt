package com.joyor.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.joyor.R
import com.joyor.databinding.SplashActivityBinding
import com.joyor.helper.moveTo

class SplashActivity : AppCompatActivity() {


    private lateinit var binding: SplashActivityBinding
    private val splashTime: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            moveTo(SignUpActivity::class.java)
        }, splashTime)


    }
}