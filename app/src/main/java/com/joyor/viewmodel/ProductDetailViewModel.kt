package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Product
import com.joyor.model.Setting
import com.joyor.service.Results
import com.joyor.service.auth.StoreService

class ProductDetailViewModel : ViewModel() {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var product: MutableLiveData<Product> = MutableLiveData()
    var isDescription: MutableLiveData<Boolean> = MutableLiveData()
    var totalCount: MutableLiveData<Int?> = MutableLiveData()
    var colorSelect: MutableLiveData<String> = MutableLiveData()

    fun onBack() {
        isBack.value = true
    }

    init {
        isDescription.value = true
        totalCount.value = 1
    }


    fun onColorSelect(colorName: String) {
        colorSelect.value = colorName
    }

    fun onMinusClick() {
        if (totalCount.value != 1)
            totalCount.value = totalCount.value!! - 1
    }

    fun onPlusClick() {
        totalCount.value = totalCount.value!! + 1
    }

    fun onDescriptionClick() {
        isDescription.value = true
    }

    fun onFeatureClick() {
        isDescription.value = false
    }


}