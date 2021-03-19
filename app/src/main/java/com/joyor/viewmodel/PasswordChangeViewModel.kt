package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.R
import com.joyor.model.User
import com.joyor.service.Results
import com.joyor.service.auth.AuthService

class PasswordChangeViewModel : ViewModel(), Results {


    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var back: MutableLiveData<Boolean> = MutableLiveData()
    var passwordChange: MutableLiveData<Boolean> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var newPassword: MutableLiveData<String> = MutableLiveData()
    var oldPassword: MutableLiveData<String> = MutableLiveData()
    var confirmPassword: MutableLiveData<String> = MutableLiveData()

    lateinit var context: Context
    private val changePasswordRq: Int = 2221


    private fun onShowProgress() {
        showProgress.value = true
    }

    private fun onDismissProgress() {
        showProgress.value = false
    }

    fun onBack() {
        back.value = true
        passwordChange.value = false
    }

    fun onPasswordChange() {
        if (isInputOk()) {
            onShowProgress()
            AuthService(changePasswordRq, this).changePassword(
                user.value?.iD!!,
                oldPassword.value!!,
                newPassword.value!!,
                confirmPassword.value!!
            )
        }
    }

    private fun isInputOk(): Boolean {
        when {
            (oldPassword.value == null || oldPassword.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.old_password_required)
                return false
            }
            (newPassword.value == null || newPassword.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.new_password_required)
                return false
            }
            (confirmPassword.value == null || confirmPassword.value!!.isEmpty()) -> {
                showToast.value = context.getString(R.string.confirm_password_required)
                return false
            }
            else -> {
                return true
            }
        }
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            changePasswordRq -> {
                passwordChange.value = true
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}
