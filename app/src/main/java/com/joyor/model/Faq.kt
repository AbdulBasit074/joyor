package com.joyor.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Faq(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("question")
    @Expose
    var question: String? = null,

    @SerializedName("answer")
    @Expose
    var answer: String? = null
) {

}