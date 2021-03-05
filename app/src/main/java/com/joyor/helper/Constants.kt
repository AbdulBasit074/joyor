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
        const val login: String = "login"
        const val register: String = "register"
        const val dataPassKey = "dataPassKey"
        const val update: String = "update"
        const val locationDialogRequestKey = 6511
        const val product: String = "product"
        const val destination: String = "destination"
        const val origin: String = "origin"
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