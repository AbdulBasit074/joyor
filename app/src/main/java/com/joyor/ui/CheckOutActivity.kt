package com.joyor.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.braintreepayments.api.models.PayPalRequest
import com.joyor.R
import com.joyor.databinding.ActivityCheckOutBinding
import com.joyor.helper.*
import com.joyor.model.Address
import com.joyor.model.CouponLines
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.CheckOutViewModel
import java.util.*
import kotlin.collections.ArrayList


class CheckOutActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context, totalAmount: String, couponLines: CouponLines): Intent {
            return Intent(context, CheckOutActivity::class.java)
                .putExtra(Constants.totalAmount, totalAmount)
                .putExtra(Constants.couponLinesTag, couponLines)
        }
    }

    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var viewModel: CheckOutViewModel
    private val countries = ArrayList<String>()
    private val requestCodePayment: Int = 1
    private var countrySelect: Int = -1
    private var totalAmountPayable: String = ""
    private lateinit var loading: CustomProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out)
        viewModel = ViewModelProviders.of(this).get(CheckOutViewModel::class.java)
        loading = CustomProgressBar(this)
        totalAmountPayable = intent.getStringExtra(Constants.totalAmount)!!
        viewModel.couponLinesList.add(intent.getParcelableExtra<CouponLines>(Constants.couponLinesTag)!!)
        viewModel.context = this
        viewModel.userLogged = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.userAddress.value = JoyorDb.newInstance(this).addressDao().getUserAddress()
        if (viewModel.userAddress.value == null)
            viewModel.userAddress.value = Address()
        viewModel.addressSave = JoyorDb.newInstance(this).addressDao().getUserAddress()
        viewModel.isCountrySelect.observe(this, Observer {
            countrySelect()
        })
        viewModel.isBack.observe(this, Observer {
            if (it)
                onBackPressed()
        })
        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.isOrderPlace.observe(this, Observer {
            makePapalPayment()
        })
        viewModel.showProgress.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })
        viewModel.orderPlaceSuccessfully.observe(this, Observer {
            if (it) {
                val dialog = DialogButton(this, getString(R.string.order_place_successfully)) { finishDetail() }
                dialog.setCancelable(false)
                dialog.show()
            }
        })
        binding.viewModel = viewModel
        getCountryFromLocale()
    }

    private fun finishDetail() {
        /**Order Place Successfully now clear all cart item & move to Home screen*/
        JoyorDb.newInstance(this).productDao().removeAllProducts()
        moveTo(HomeActivity::class.java)
        finishAffinity()
    }

    private fun getCountryFromLocale() {
        /*** Get All Country Name From locale*/
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
        val adapter: ArrayAdapter<String> = ArrayAdapter(binding.root.context, R.layout.support_simple_spinner_dropdown_item, countries)
        builder.setAdapter(adapter) { _, which ->
            binding.countryRegionDialog.text = countries[which]
            countrySelect = which
        }
        val dialog: AlertDialog = builder.create()
        dialog.setCustomTitle(binding.root.context.customTextView(getString(R.string.select_country)))
        dialog.show()
    }

    private fun makePapalPayment() {
        /*** Make Payment Through BrainTree Paypal Using DropIn UI*/
        val papalRequest: PayPalRequest = PayPalRequest(totalAmountPayable)
            .currencyCode(getString(R.string.euro_currency))
        val dropInRequest: DropInRequest = DropInRequest().clientToken(viewModel.clientToken).paypalRequest(papalRequest)
        startActivityForResult(dropInRequest.getIntent(this), requestCodePayment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //For Request Code Paypal Nonce get
        if (requestCode == requestCodePayment) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    /**get nonce from brain tree client sdk and pass to server*/
                    val result: DropInResult = data?.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT)!!
                    viewModel.saveNonceKey(result.paymentMethodNonce?.nonce!!)
                }
                Activity.RESULT_CANCELED -> {
                    viewModel.showToast.value = getString(R.string.cancle_process)
                }
                else -> {
                    val error = data?.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                    viewModel.showToast.value = error.toString()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.orderPlaceSuccessfully.value!!)
            finishDetail()
        else
            super.onBackPressed()
    }
}