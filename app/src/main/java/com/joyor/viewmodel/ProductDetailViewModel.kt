package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.helper.Constants
import com.joyor.helper.Persister
import com.joyor.model.Product
import com.joyor.service.Results
import com.joyor.service.store.StoreService
import org.json.JSONArray

class ProductDetailViewModel : ViewModel(), Results {

    var getProductDetailsRc = 7429
    var showToast: MutableLiveData<String> = MutableLiveData()
    var isBack: MutableLiveData<Boolean> = MutableLiveData()
    var product: MutableLiveData<Product> = MutableLiveData()
    var isDescription: MutableLiveData<Boolean> = MutableLiveData()
    var totalCount: MutableLiveData<Int?> = MutableLiveData()
    var colorSelect: MutableLiveData<Product.Option?> = MutableLiveData()
    var addToCart: MutableLiveData<Boolean> = MutableLiveData()
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()

    fun getProductDetails(productId:Int?){
        onShowProgress()
        StoreService(getProductDetailsRc, this).getProductDetails(productId)
    }

    private fun onShowProgress() {
        progressBar.value = true
    }

    private fun onDismissProgress() {
        progressBar.value = false
    }

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

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            getProductDetailsRc -> {
                val responseArray = JSONArray(data)
                product.value = Gson().fromJson(responseArray.get(0).toString(), Product::class.java)
                onDismissProgress()
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }


}