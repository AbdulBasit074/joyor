package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.R
import com.joyor.helper.Constants
import com.joyor.model.Address
import com.joyor.model.CouponLines
import com.joyor.model.PlaceOrder
import com.joyor.model.User
import com.joyor.model.room.JoyorDb
import com.joyor.service.Results
import com.joyor.service.auth.AuthService
import com.joyor.service.order.OrderService
import org.json.JSONObject
import java.util.regex.Pattern

class CheckOutViewModel : ViewModel(), Results {

    var userAddress: MutableLiveData<Address> = MutableLiveData()
    var checkOut: MutableLiveData<Boolean> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var additionalInfo: MutableLiveData<String?> = MutableLiveData()
    var isManual: MutableLiveData<Boolean> = MutableLiveData()
    var isCountrySelect: MutableLiveData<Boolean> = MutableLiveData()
    var orderPlaceSuccessfully: MutableLiveData<Boolean> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var isOrderPlace: MutableLiveData<Boolean> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var orderParams: PlaceOrder = PlaceOrder()
    lateinit var clientToken: String
    var addressSave: Address? = Address()
    var userLogged: User? = null
    private var updateUserAddressRq: Int = 1111
    private var cashOnDeliveryOrderRq: Int = 3111
    private var onlinePaymentOrderRq: Int = 4212
    private var onlineNonceKeySaveRq: Int = 2212
    private var onlinePaymentOrderRqToken: Int = 4222
    lateinit var context: Context
    var couponLinesList: ArrayList<CouponLines>

    init {
        userAddress.value = Address()
        couponLinesList = ArrayList()
        additionalInfo.value = ""
        isManual.value = true
        checkOut.value = false
        orderPlaceSuccessfully.value = false
    }

    private fun onShowProgress() {
        showProgress.value = true
    }

    private fun onDismissProgress() {
        showProgress.value = false
    }

    private fun allInputIsOk(): Boolean {
        when {
            userAddress.value == null || userAddress.value!!.billing!!.firstName!!.isEmpty() -> {
                showToast.value = context.getString(R.string.first_name_required)
                return false
            }
            userAddress.value!!.billing!!.lastName!!.isEmpty() -> {
                showToast.value = context.getString(R.string.last_name_required)
                return false
            }
            userAddress.value!!.billing!!.country!!.isEmpty() -> {
                showToast.value = context.getString(R.string.country_required)
                return false
            }
            userAddress.value!!.billing!!.address1!!.isEmpty() -> {
                showToast.value = context.getString(R.string.street_required)
                return false
            }
            userAddress.value!!.billing!!.city!!.isEmpty() -> {
                showToast.value = context.getString(R.string.town_required)
                return false
            }
            userAddress.value!!.billing!!.postcode!!.isEmpty() -> {
                showToast.value = context.getString(R.string.post_required)
                return false
            }
            userAddress.value!!.billing!!.phone!!.isEmpty() -> {
                showToast.value = context.getString(R.string.phone_required)
                return false
            }
            userAddress.value!!.billing?.email!!.isEmpty() -> {
                showToast.value = context.getString(R.string.email_required)
                return false
            }
            !Pattern.compile(Constants.emailPattern, Pattern.CASE_INSENSITIVE)
                .matcher(userAddress.value!!.billing?.email!!).matches() -> {
                showToast.value = context.getString(R.string.email_required)
                return false
            }
            else ->
                return true
        }
    }

    fun onCountrySelect() {
        isCountrySelect.value = true
    }

    fun onManual() {
        isManual.value = true
    }

    fun onOnline() {
        isManual.value = false
    }

    fun onBack() {
        isBack.value = true
    }

    fun onPlaceOrder() {
        if (allInputIsOk()) {
            /**
             * On Place Order Save Address FOR login User IN CASE
             * save Address not found for user
             * user first Name, country, email address is change from previous detail
             * otherwise order place directly
             */

            if (userLogged != null && (userAddress.value == null || addressSave==null || userAddress.value!!.billing!!.firstName != addressSave!!.billing!!.firstName || userAddress.value!!.billing!!.country != addressSave!!.billing!!.country || userAddress.value!!.billing!!.email != addressSave!!.billing!!.email)) {
                userAddress.value!!.userId = userLogged!!.iD
                onShowProgress()
                AuthService(updateUserAddressRq, this).addUserAddress(userAddress.value!!)
            } else {
                onShowProgress()
                orderPlaceRequest()
            }
        }
    }

    fun saveNonceKey(nonceKey: String) {
        /**brain tree nonce key pass to server for confirmation*/
        onShowProgress()
        OrderService(onlineNonceKeySaveRq, this).saveNonceKey(nonceKey)
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            updateUserAddressRq -> {
                JoyorDb.newInstance(context).addressDao().deleteAddress()
                JoyorDb.newInstance(context).addressDao().getUserAddress()
                JoyorDb.newInstance(context).addressDao().addAddress(userAddress.value!!)
                orderPlaceRequest()
            }
            cashOnDeliveryOrderRq -> {
                orderPlaceSuccessfully.value = true
            }
            onlinePaymentOrderRq -> {
                orderPlaceSuccessfully.value = true
            }
            onlineNonceKeySaveRq -> {
                createParams()
                orderParams.paymentMethod = Constants.brainTreePaypal
                OrderService(onlinePaymentOrderRq, this).orderPlace(orderParams)
            }
            onlinePaymentOrderRqToken -> {
                /**Client token to call client Sdk of brain tress*/
                val jsonObj = JSONObject(data)
                clientToken = jsonObj.getString(Constants.clientToken)
                isOrderPlace.value = true
            }
        }
    }

    private fun orderPlaceRequest() {
        /**
         * check place order through cash on delivery or online
         * cash- place order direct
         * online- get client token from server for process paypal method
         **/
        if (isManual.value!!) {
            createParams()
            orderParams.paymentMethod = Constants.cheque
            OrderService(cashOnDeliveryOrderRq, this).orderPlace(orderParams)
        } else
            OrderService(onlinePaymentOrderRqToken, this).getClientToken()

    }

    private fun createParams() {
        /**Creating Params for order Place*/
        orderParams.billing = userAddress.value!!.billing
        orderParams.additional_information = additionalInfo.value
        orderParams.couponLine = couponLinesList
        val cartProduct = JoyorDb.newInstance(context).productDao().getAllCartProduct()
        cartProduct.forEach {
            val idVariations: Int? = if (it.colorSelect.id == 0) null else it.colorSelect.id
            val lineItem: PlaceOrder.LineItem = PlaceOrder.LineItem(it.id, it.quantity, idVariations)
            orderParams.lineItems?.add(lineItem)
        }
        orderParams.shippingLines = PlaceOrder.ShippingLines()
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}