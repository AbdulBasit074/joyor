package com.joyor.helper

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

class Constants {

    companion object {
        const val baseUrl: String = "https://joyor.dconcepts.business/wp-json/trs-api/v1/"
        const val googleMapUrl: String = "https://maps.googleapis.com/maps/api/"
        const val user: String = "user"
        const val totalAmount: String = "totalAmount"
        const val login: String = "login"
        const val register: String = "register"
        const val fcmTokenPersistenceKey: String = "fcmTokenPersistenceKey"
        const val dataPassKey = "dataPassKey"
        const val configClientId = "sandbox_7brrn4n9_7r3bkvzthh9gp8g4"
        const val update: String = "update"
        const val productDataPersists: String = "productListPersists"
        const val settingDataPersists: String = "settingDataPersists"
        const val dealerDataPersists: String = "dealerDataPersists"
        const val cheque: String = "cheque"
        const val brainTreePaypal: String = "braintree_paypal"
        const val clientToken: String = "clientToken"
        const val selectedLanguage: String = "selectedLanguage"
        const val consumerKey: String = "ck_f98ffa65905adc453ae1b0b9b33ea56e50ca9f3d"
        const val emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        const val consumerSecret: String = "cs_f191226a6faaaea35d893d8a708644e7b1e0c341"
        const val locationDialogRequestKey = 6511
        const val product: String = "product"
        const val splashTime: Long = 2000
        const val userProductRegister: String = "userProductRegister"
        const val couponLinesTag: String = "couponLines"
        const val destination: String = "destination"
        const val origin: String = "origin"
        const val euro: String = "EUR"
        const val purchaseProduct: String = "Purchase Products"
        const val signUpFirstShow: String = "SignUpFirstShow"


        fun geoCoding(latitude: Double, longitude: Double, context: Context): String {
            val addresses: List<Address>
            val geoCoder = Geocoder(context, Locale.getDefault())

            addresses = geoCoder.getFromLocation(
                latitude,
                longitude,
                1
            )
            val address = addresses[0]
                .getAddressLine(0)
            if (address.isNotEmpty()) {
                address.substring(0, address.length - 1)
            }
            return address.toString()
        }

    }
}