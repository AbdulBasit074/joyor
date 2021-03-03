package com.joyor.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.SplashActivityBinding
import com.joyor.helper.Constants
import com.joyor.helper.moveTo
import com.joyor.helper.moveToAndFinish
import com.joyor.model.User
import com.joyor.model.room.JoyorDb
import com.joyor.service.auth.AuthService
import com.joyor.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {


    private lateinit var binding: SplashActivityBinding
    private lateinit var viewModel: SplashViewModel
    private val splashTime: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (!androidx.preference.PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.signUpFirstShow, false))
                moveToAndFinish(SignUpActivity::class.java)
            else {
                viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
            }
        }, splashTime)
        viewModel.user.observe(this, Observer {

            if (viewModel.updateUser) {
                JoyorDb.newInstance(this).userDao().logOut()
                JoyorDb.newInstance(this).userDao().login(it!!)
                moveToAndFinish(HomeActivity::class.java)
            } else if (it != null)
                viewModel.getUserUpdateDetail()
            else
                moveToAndFinish(HomeActivity::class.java)


        })
    }
}