package com.joyor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.EditProfileActivityBinding
import com.joyor.helper.UserLoginEventBus
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.EditProfileViewModel
import org.greenrobot.eventbus.EventBus

class EditProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: EditProfileActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_activity)
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()


        viewModel.user.observe(this, Observer {
            if (it != null) {
                JoyorDb.newInstance(this).userDao().logOut()
                JoyorDb.newInstance(this).userDao().login(it)
                EventBus.getDefault().postSticky(UserLoginEventBus(it))
            }
        })

        viewModel.showToast.observe(this, Observer { it ->
            showToast(it)
        })

        viewModel.isLogout.observe(this, Observer {
            if (it) {
                JoyorDb.newInstance(this).userDao().logOut()
                EventBus.getDefault().postSticky(UserLoginEventBus(null))
                finish()
            }
        })

        viewModel.onBack.observe(this, Observer {
            finish()
        })

        viewModel.isUpdate.observe(this, Observer {
            JoyorDb.newInstance(this).userDao().logOut()
            JoyorDb.newInstance(this).userDao().login(viewModel.user.value!!)
            EventBus.getDefault().postSticky(UserLoginEventBus(viewModel.user.value!!))
            showToast("Profile Update")
        })


    }

}