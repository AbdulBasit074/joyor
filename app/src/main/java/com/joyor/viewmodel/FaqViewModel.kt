package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Faq
import com.joyor.service.Results
import com.joyor.service.setting.SettingService

class FaqViewModel : ViewModel(), Results {

    var faqListing: MutableLiveData<ArrayList<Faq>> = MutableLiveData()
    private var showToast: MutableLiveData<String> = MutableLiveData()
    private val faqRequestValue: Int = 2233
    var back: MutableLiveData<Boolean> = MutableLiveData()

    init {
        onFaqListing()
    }

    fun onBack() {
        back.value = true
    }

    private fun onFaqListing() {
        SettingService(faqRequestValue, this).getFaq()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            faqRequestValue -> {
                faqListing.value = Gson().fromJson(data, object : TypeToken<ArrayList<Faq>>() {}.type)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }

}