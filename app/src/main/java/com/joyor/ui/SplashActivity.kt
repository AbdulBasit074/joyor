package com.joyor.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.SplashActivityBinding
import com.joyor.helper.Constants
import com.joyor.helper.moveToAndFinish
import com.joyor.helper.setLanguage
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.context = this

        Handler(Looper.getMainLooper()).postDelayed({
            if (!androidx.preference.PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.signUpFirstShow, false))
                moveToAndFinish(SignUpActivity::class.java)
            else {
                viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
            }
        }, Constants.splashTime)

        viewModel.user.observe(this, Observer {

            when {
                viewModel.updateUser -> {
                    JoyorDb.newInstance(this).userDao().logOut()
                    JoyorDb.newInstance(this).userDao().login(it!!)
                    moveToAndFinish(HomeActivity::class.java)
                }
                it != null -> viewModel.getUserUpdateAddress()
                else -> moveToAndFinish(HomeActivity::class.java)
            }
        })
    }

}