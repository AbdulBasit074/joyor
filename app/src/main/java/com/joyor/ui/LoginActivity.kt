package com.joyor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityLoginBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.UserLoginEventBus
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.LoginSignUpViewModel
import org.greenrobot.eventbus.EventBus

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginSignUpViewModel
    private lateinit var loading: CustomProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loading = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel::class.java)
        viewModel.context = this
        binding.viewModel = viewModel
        viewModel.isLogin.observe(this, Observer {
            TransitionManager.beginDelayedTransition(binding.parentView)
            if (it) {
                binding.loginBtn.text = getString(R.string.login)
                binding.login.background =
                    ContextCompat.getDrawable(this, R.drawable.round_border_brown)
                binding.signUp.background =
                    ContextCompat.getDrawable(this, R.drawable.round_border_white)
                binding.login.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.signUp.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.sureName.visibility = View.INVISIBLE
                binding.name.visibility = View.INVISIBLE
                binding.email.text.clear()
                binding.password.text.clear()
                binding.email.visibility = View.VISIBLE
                binding.password.visibility = View.VISIBLE

            } else {

                binding.loginBtn.text = getString(R.string.register)
                binding.login.background =
                    ContextCompat.getDrawable(this, R.drawable.round_border_white)
                binding.login.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.signUp.background =
                    ContextCompat.getDrawable(this, R.drawable.round_border_brown)
                binding.signUp.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.name.requestFocus()
                binding.email.text.clear()
                binding.name.text.clear()
                binding.sureName.text.clear()
                binding.password.text.clear()
                binding.sureName.visibility = View.VISIBLE
                binding.name.visibility = View.VISIBLE
                binding.email.visibility = View.VISIBLE
                binding.password.visibility = View.VISIBLE
            }
        })
        viewModel.saveRegister.observe(this, Observer {
            JoyorDb.newInstance(this).registerProduct().removeAll()
            JoyorDb.newInstance(this).registerProduct().addRegisterProduct(it)
        })
        viewModel.showProgress.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })
        viewModel.isSignUp.observe(this, Observer {
            JoyorDb.newInstance(this).registerProduct().removeAll()
            finish()
        })
        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.isBack.observe(this, Observer { finish() })
        viewModel.userAddress.observe(this, Observer {
            if (it != null) {
                JoyorDb.newInstance(this).addressDao().deleteAddress()
                JoyorDb.newInstance(this).addressDao().addAddress(it)
                finish()
            }
        })
        viewModel.user.observe(this, Observer {
            JoyorDb.newInstance(this).userDao().logOut()
            JoyorDb.newInstance(this).userDao().login(it)
            EventBus.getDefault().postSticky(UserLoginEventBus(it))
        })
    }
}