package com.joyor.ui

import android.os.Bundle
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
    private var language = arrayOf("en", "es")
    private lateinit var loading: CustomProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_activity)
        loading = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        viewModel.context = this
        binding.viewModel = viewModel
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.addressOpen.observe(this, Observer {
            moveTo(AddressActivity::class.java)
        })
        viewModel.isProgress.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })
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
        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.isLogout.observe(this, Observer {
            if (it) {
                JoyorDb.newInstance(this).addressDao().deleteAddress()
                JoyorDb.newInstance(this).registerProduct().removeAll()
                Persister(this).persist(Constants.userProductRegister, false)
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

        val builder = AlertDialog.Builder(this, R.style.DialogTheme)
        var selectedItem = 0
        if (Persister.with(this).getPersisted(Constants.selectedLanguage, "en") != "en")
            selectedItem = 1
        builder.setSingleChoiceItems(resources.getStringArray(R.array.lang), selectedItem) { _, which ->
            Persister.with(this).persist(Constants.selectedLanguage, language[which])
            setLanguage()
            moveTo(SplashActivity::class.java)
            finishAffinity()
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_country)))
        dialog.show()

    }

}