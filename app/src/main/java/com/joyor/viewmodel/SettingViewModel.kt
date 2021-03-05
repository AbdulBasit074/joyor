package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Setting
import com.joyor.service.Results
import com.joyor.service.setting.SettingService

class SettingViewModel : ViewModel(), Results {


    var settingListing: MutableLiveData<ArrayList<Setting>> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    var webSiteUrl: MutableLiveData<String> = MutableLiveData()
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    var openFAQ: MutableLiveData<Boolean> = MutableLiveData()
    private val onGetSettingRequest: Int = 2273

    init {
        getSettingList()
    }

    private fun onShowProgress() {
        progressBar.value = true
    }

    private fun onDismissProgress() {
        progressBar.value = false
    }

    fun onWebsiteClick(website: String, id: Int) {
        if (id != 3362)
            webSiteUrl.value = website
        else
            openFAQ.value = true
    }

    private fun getSettingList() {
        onShowProgress()
        SettingService(onGetSettingRequest, this).getSetting()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (onGetSettingRequest) {
            onGetSettingRequest -> {
                onDismissProgress()
                settingListing.value = Gson().fromJson(data, object : TypeToken<ArrayList<Setting>>() {}.type)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}