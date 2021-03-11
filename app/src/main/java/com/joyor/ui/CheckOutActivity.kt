package com.joyor.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityCheckOutBinding
import com.joyor.helper.customTextView
import com.joyor.helper.showToast
import com.joyor.viewmodel.CheckOutViewModel
import java.util.*

class CheckOutActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var viewModel: CheckOutViewModel
    private val countries = ArrayList<String>()
    private var countrySelect: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out)
        viewModel = ViewModelProviders.of(this).get(CheckOutViewModel::class.java)
        viewModel.context = this
        binding.viewModel = viewModel
        getCountryFromLocale()
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
        viewModel.isOrderPlace.observe(this, Observer {
            showToast("Order Place ")
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