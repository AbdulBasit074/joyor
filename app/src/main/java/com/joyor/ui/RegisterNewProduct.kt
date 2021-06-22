package com.joyor.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.ActivityRegisterNewProductBinding
import com.joyor.helper.*
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.RegisterNewProductViewModel
import java.util.*


class RegisterNewProduct : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterNewProductBinding
    private lateinit var viewModel: RegisterNewProductViewModel
    private val countries = ArrayList<String>()
    private val models = ArrayList<String>()
    private lateinit var progressBar: CustomProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_new_product)
        viewModel = ViewModelProviders.of(this).get(RegisterNewProductViewModel::class.java)
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.context = this
        progressBar = CustomProgressBar(this)
        initModels()
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
        viewModel.isModelSelect.observe(this, Observer {
            modelSelect()
        })
        viewModel.isCountrySelect.observe(this, Observer {
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
        val dpd = DatePickerDialog(this, R.style.DialogTheme, { _, _, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1
            binding.datePurchase.text = getString(R.string.date_format, year.toString(), month.toString(), dayOfMonth.toString())
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

    private fun initModels() {
        //F1, F3, F5+/F5, F5S+/F5S, A1, A3, A5, X1, X5S, Y5S, Y10, G1, G5, H1, Mbike
        models.add("F1")
        models.add("F3")
        models.add("F5")
        models.add("F5S")
        models.add("A1")
        models.add("A3")
        models.add("A5")
        models.add("X1")
        models.add("X5S")
        models.add("Y5S")
        models.add("Y10")
        models.add("G1")
        models.add("G5")
        models.add("H1")
        models.add("Mbike")
    }

    private fun modelSelect() {
        /**select model**/
        val builder = AlertDialog.Builder(binding.root.context)
        val adapter = ArrayAdapter(binding.root.context, R.layout.support_simple_spinner_dropdown_item, models)
        builder.setAdapter(adapter) { _, which ->
            binding.model.text = models[which]
            viewModel.model.value = models[which]
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.model)))
        dialog.show()
    }

    private fun countrySelect() {
        /**select country**/
        val builder = AlertDialog.Builder(binding.root.context)
        val adapter = ArrayAdapter(binding.root.context, R.layout.support_simple_spinner_dropdown_item, countries)
        builder.setAdapter(adapter) { _, which ->
            binding.country.text = countries[which]
            viewModel.country.value = countries[which]
        }
        val dialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_country)))
        dialog.show()
    }
}