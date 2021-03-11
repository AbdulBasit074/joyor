package com.joyor.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


@Entity
class Address(
    @PrimaryKey
    @SerializedName("user_id") @Expose var userId: Int? = 0,
    @Embedded(prefix = "billing_")
    @SerializedName("billing") @Expose var billing: Billing? = Billing()

) {
    class Billing(
        @SerializedName("first_name") @Expose var firstName: String? = "",
        @SerializedName("last_name") @Expose var lastName: String? = "",
        @SerializedName("company") @Expose var company: String? = "",
        @SerializedName("address_1") @Expose var address1: String? = "",
        @SerializedName("address_2") @Expose var address2: String? = "",
        @SerializedName("city") @Expose var city: String? = "",
        @SerializedName("state") @Expose var state: String? = "",
        @SerializedName("postcode") @Expose var postcode: String? = "",
        @SerializedName("country") @Expose var country: String? = "",
        @SerializedName("email") @Expose var email: String? = "",
        @SerializedName("phone") @Expose var phone: String? = ""
    ) {}

}