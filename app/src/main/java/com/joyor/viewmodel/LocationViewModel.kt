package com.joyor.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Store
import com.joyor.service.Results
import com.joyor.service.auth.StoreService

class LocationViewModel : ViewModel(), Results {

    var webSiteUrl: MutableLiveData<String> = MutableLiveData()
    var storeListing: MutableLiveData<ArrayList<Store>> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()

    private val getStoreRequest: Int = 2211
    fun onWebsiteClick(website: String) {
        webSiteUrl.value = website
    }
    init {
        getStoreList()
    }

    private fun getStoreList() {
        StoreService(getStoreRequest, this).getStore()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            getStoreRequest -> {
                storeListing.value =
                    Gson().fromJson(data, object : TypeToken<ArrayList<Store>>() {}.type)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }
}