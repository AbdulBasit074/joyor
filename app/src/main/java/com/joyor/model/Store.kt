package com.joyor.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Store(

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null,

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null,

    @SerializedName("telephone")
    @Expose
    var telephone: String? = null,

    @SerializedName("website")
    @Expose
    var website: String? = null,

    @SerializedName("address")
    @Expose
    var address: String? = null,

    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null

) {}