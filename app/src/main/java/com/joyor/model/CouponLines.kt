package com.joyor.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CouponLines(
    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("amount")
    @Expose
    var question: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(question)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CouponLines> {
        override fun createFromParcel(parcel: Parcel): CouponLines {
            return CouponLines(parcel)
        }

        override fun newArray(size: Int): Array<CouponLines?> {
            return arrayOfNulls(size)
        }
    }
}