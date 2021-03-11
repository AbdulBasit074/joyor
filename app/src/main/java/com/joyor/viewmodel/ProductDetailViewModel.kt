package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joyor.model.Product

class ProductDetailViewModel : ViewModel() {

    var showToast: MutableLiveData<String> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var product: MutableLiveData<Product> = MutableLiveData()
    var isDescription: MutableLiveData<Boolean> = MutableLiveData()
    var totalCount: MutableLiveData<Int?> = MutableLiveData()
    var colorSelect: MutableLiveData<Product.Option?> = MutableLiveData()
    var addToCart: MutableLiveData<Boolean> = MutableLiveData()

    fun onBack() {
        isBack.value = true
    }

    init {
        addToCart.value = false
        isDescription.value = true
        totalCount.value = 1
        colorSelect.value = Product.Option()
    }

    fun addToCartProduct() {
        addToCart.value = true

    }

    fun onColorSelect(color: Product.Option) {
        colorSelect.value = color
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