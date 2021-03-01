package com.joyor.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Setting(

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("content")
    @Expose
    var content: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null,

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null,

    @SerializedName("button_text")
    @Expose
    var buttonText: String? = null
) {}