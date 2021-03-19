package com.joyor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityPasswordUpdateBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.DialogButton
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.PasswordChangeViewModel

class PasswordUpdate : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordUpdateBinding
    private lateinit var viewModel: PasswordChangeViewModel
    private lateinit var loading: CustomProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_update)
        loading = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(PasswordChangeViewModel::class.java)
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
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
        viewModel.passwordChange.observe(this, Observer {
            if (it) {
                val dialog = DialogButton(this, getString(R.string.password_change_successfully)) { onChangeData() }
                dialog.show()
            }
        })
    }
    private fun onChangeData() {
        viewModel.newPassword.value = ""
        finish()
    }
}