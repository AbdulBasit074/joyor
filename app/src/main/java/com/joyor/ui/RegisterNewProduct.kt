package com.joyor.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityRegisterNewProductBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.DialogButton
import com.joyor.helper.customTextView
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.RegisterNewProductViewModel
import java.util.*


class RegisterNewProduct : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterNewProductBinding
    private lateinit var viewModel: RegisterNewProductViewModel
    private val countries = ArrayList<String>()
    private var countrySelect: Int = -1
    private lateinit var progressBar: CustomProgressBar
    private val myCalendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_new_product)
        viewModel = ViewModelProviders.of(this).get(RegisterNewProductViewModel::class.java)
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.context = this
        progressBar = CustomProgressBar(this)
        getCountryFromLocale()
        viewModel.back.observe(this, Observer {
            finish()
        })
        viewModel.registerProduct.observe(this, Observer {
            if (it) {
                val dialog = DialogButton(this, getString(R.string.product_register_succuessfully)) {
                    setResult(Activity.RESULT_OK)
                    onBackPressed()
                }
                dialog.setCancelable(false)
                dialog.show()
            }
        })
        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.isCountrySelet.observe(this, Observer {
            countrySelect()
        })
        viewModel.showProgress.observe(this, Observer {
            if (it)
                progressBar.show()
            else
                progressBar.dismiss()
        })
        viewModel.datePurchase.observe(this, Observer {
            if (it) {
                showDatePicker()
            }
        })
        binding.viewModel = viewModel
    }

    private fun showDatePicker() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, R.style.DialogTheme, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            binding.datePurchase.text = "$dayOfMonth-$monthOfYear-$year"
        }, year, month, day)
        dpd.show()
    }

    private fun getCountryFromLocale() {
        /**get Countries from local*/
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
            binding.country.text = countries[which]
            viewModel.country.value = countries[which]
            countrySelect = which
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_country)))
        dialog.show()
    }
}