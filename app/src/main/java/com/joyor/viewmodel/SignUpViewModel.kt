package com.joyor.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.joyor.helper.moveTo
import com.joyor.ui.HomeActivity


class SignUpViewModel : ViewModel() {


    fun onClick(view: View) {
        view.context.moveTo(HomeActivity::class.java)
    }
}