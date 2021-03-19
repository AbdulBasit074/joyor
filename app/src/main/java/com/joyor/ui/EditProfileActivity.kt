package com.joyor.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.EditProfileActivityBinding
import com.joyor.helper.*
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.EditProfileViewModel
import org.greenrobot.eventbus.EventBus


class EditProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var binding: EditProfileActivityBinding
    private var language = arrayOf("en", "fr", "pt", "es")
    private lateinit var loading: CustomProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_activity)
        loading = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        viewModel.context = this
        binding.viewModel = viewModel
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.addressOpen.observe(this, Observer {
            moveTo(AddressActivity::class.java)
        })

        viewModel

        viewModel.user.observe(this, Observer {
            if (it != null) {
                JoyorDb.newInstance(this).userDao().logOut()
                JoyorDb.newInstance(this).userDao().login(it)
                EventBus.getDefault().postSticky(UserLoginEventBus(it))
            }
        })
        viewModel.languageSelect.observe(this, Observer {
            selectLanguage()
        })
        viewModel.showToast.observe(this, Observer { it ->
            showToast(it)
        })
        viewModel.isLogout.observe(this, Observer {
            if (it) {
                JoyorDb.newInstance(this).addressDao().deleteAddress()
                JoyorDb.newInstance(this).userDao().logOut()
                EventBus.getDefault().postSticky(UserLoginEventBus(null))
                finish()
            }
        })
        viewModel.onBack.observe(this, Observer {
            finish()
        })
        viewModel.changePassword.observe(this, Observer {
            moveTo(PasswordUpdate::class.java)
        })
        viewModel.registerProduct.observe(this, Observer {
            if (it)
                moveTo(RegisterProduct::class.java)
        })
        viewModel.isUpdate.observe(this, Observer {
            JoyorDb.newInstance(this).userDao().logOut()
            JoyorDb.newInstance(this).userDao().login(viewModel.user.value!!)
            EventBus.getDefault().postSticky(UserLoginEventBus(viewModel.user.value!!))
            showToast(getString(R.string.update_address))
        })
    }
    private fun selectLanguage() {
        val builder = AlertDialog.Builder(this)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.country))
        builder.setAdapter(adapter) { _, which ->
            setLanguage(language[which])
            moveTo(HomeActivity::class.java)
            finishAffinity()
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_country)))
        dialog.show()
    }

}