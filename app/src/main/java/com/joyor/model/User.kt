package com.joyor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity
class User(

    @PrimaryKey
    @SerializedName("ID")
    @Expose
    var iD: Int? = null,
    @SerializedName("username")
    @Expose
    var username: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null
) {

}