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
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    var searchList: MutableLiveData<ArrayList<Store>> = MutableLiveData()


    private val getStoreRequest: Int = 2211
    fun onWebsiteClick(website: String) {
        webSiteUrl.value = website
    }

    private fun onShowProgress() {
        progressBar.value = true
    }

    private fun onDismissProgress() {
        progressBar.value = false
    }

    init {
        getStoreList()
    }

    private fun getStoreList() {
        onShowProgress()
        StoreService(getStoreRequest, this).getStore()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        onDismissProgress()
        when (requestCode) {
            getStoreRequest -> {
                storeListing.value =
                    Gson().fromJson(data, object : TypeToken<ArrayList<Store>>() {}.type)
                searchList.value = Gson().fromJson(data, object : TypeToken<ArrayList<Store>>() {}.type)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}