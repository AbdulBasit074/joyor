package com.joyor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.joyor.R
import com.joyor.databinding.SignupLayoutBinding
import com.joyor.helper.Constants
import com.joyor.helper.moveToAndFinish
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: SignupLayoutBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.signup_layout)
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()

        binding.viewModel = viewModel
        viewModel.saveUserInDb = false

        viewModel.user.observe(this, Observer {
            if (viewModel.saveUserInDb)
                JoyorDb.newInstance(this).userDao().login(it)
            if (it != null)
                moveToAndFinish(HomeActivity::class.java)

            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(Constants.signUpFirstShow, true).apply()
        })

        viewModel.showToast.observe(this, Observer { it ->
            showToast(it)
        })

        viewModel.name.observe(this, Observer {
            if (it.isNotEmpty())
                setButtonRegister()
            else
                setButtonSkip()
        })

        viewModel.nameSure.observe(this, Observer {
            if (it.isNotEmpty())
                setButtonRegister()
            else
                setButtonSkip()

        })

        viewModel.email.observe(this, Observer {

            if (it.isNotEmpty())
                setButtonRegister()
            else
                setButtonSkip()

        })

        viewModel.password.observe(this, Observer {
            if (it.isNotEmpty())
                setButtonRegister()
            else
                setButtonSkip()

        })

    }

    private fun setButtonRegister() {
        binding.skipBtn.text = getString(R.string.register)
        viewModel.isSkip = false
    }

    private fun setButtonSkip() {
        binding.skipBtn.text = getString(R.string.skip)
        viewModel.isSkip = true
    }
}