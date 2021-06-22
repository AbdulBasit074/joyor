package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.R
import com.joyor.service.Results
import com.joyor.service.auth.AuthService

class ForgotPasswordViewModel : ViewModel(), Results {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var showDialog: MutableLiveData<String> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var back: MutableLiveData<Boolean> = MutableLiveData()
    var email: MutableLiveData<String> = MutableLiveData()
    private val forgotPasswordRq: Int = 2221
    lateinit var context: Context

    private fun onShowProgress() {
        showProgress.value = true
    }

    private fun onDismissProgress() {
        showProgress.value = false
    }

    fun onBack() {
        back.value = true
    }

    fun onSubmit() {
        if (isInputOk()) {
            onShowProgress()
            AuthService(forgotPasswordRq, this).forgotPassword(email.value!!)
        }
    }

    private fun isInputOk(): Boolean {
        return when {
            (email.value == null || email.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.password_required)
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            forgotPasswordRq -> {
                showDialog.value = data
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}
