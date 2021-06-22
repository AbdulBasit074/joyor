package com.joyor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityForgotPasswordBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.DialogButton
import com.joyor.helper.setLanguage
import com.joyor.helper.showToast
import com.joyor.viewmodel.ForgotPasswordViewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var loading: CustomProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        loading = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)
        viewModel.context = this
        binding.viewModel = viewModel

        viewModel.back.observe(this, Observer {
            if (it)
                finish()
        })
        viewModel.showProgress.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })
        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.showDialog.observe(this, Observer {
            val dialog = DialogButton(this, it) { onChangeData() }
            dialog.show()
        })
    }

    private fun onChangeData() {
        finish()
    }
}