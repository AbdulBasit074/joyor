package com.joyor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity
class RegisterProduct(
    @PrimaryKey
    @SerializedName("id") @Expose var id: String = "",
    @SerializedName("model") @Expose var model: String? = null,
    @SerializedName("serial_number") @Expose var serialNumber: String? = null,
    @SerializedName("country") @Expose var country: String? = null,
    @SerializedName("date_purchase") @Expose var datePurchase: String? = null,
    @SerializedName("date_created") @Expose var dateCreated: String? = null,
    @SerializedName("user_id") @Expose var userId: String? = null
) {}