package com.joyor.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityAddressBinding
import com.joyor.helper.customTextView
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.AddressViewModel
import java.util.*

class AddressActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddressBinding
    private lateinit var viewModel: AddressViewModel
    private val countries = ArrayList<String>()
    private var countrySelect: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address)
        viewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        viewModel.context = this
        viewModel.userAddress.value = JoyorDb.newInstance(this).addressDao().getUserAddress()
        viewModel.userLogged.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        binding.viewModel = viewModel
        getCountryFromLocale()
        viewModel.userAddress.observe(this, Observer {
            if (it!=null && it.userId != 0) {
                binding.addressBtn.text = getString(R.string.update_address)
            }
        })
        viewModel.isCountrySelect.observe(this, Observer {
            countrySelect()
        })

        viewModel.isBack.observe(this, Observer {
            if (it) {
                finish()
            }
        })
        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })

    }

    private fun getCountryFromLocale() {
        val locales = Locale.getAvailableLocales()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
    }

    private fun countrySelect() {
        /**select country**/
        val builder = AlertDialog.Builder(binding.root.context)
        val adapter = ArrayAdapter(binding.root.context, R.layout.support_simple_spinner_dropdown_item, countries)
        builder.setAdapter(adapter) { _, which ->
            binding.countryRegionDialog.text = countries[which]
            countrySelect = which
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_country)))
        dialog.show()
    }

}