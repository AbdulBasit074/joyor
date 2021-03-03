package com.joyor.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.model.Product
import com.joyor.model.Setting
import com.joyor.service.Results
import com.joyor.service.auth.StoreService

class ProductViewModel : ViewModel(), Results {

    var productListing: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    var product: MutableLiveData<Product> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    private val productListRequest = 6623

    init {
        getProducts()
    }

    fun onProductClick(productClick: Product) {
        product.value = productClick
    }
    private fun getProducts() {
        StoreService(productListRequest, this).getProducts()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            productListRequest -> {
                productListing.value = Gson().fromJson(data, object : TypeToken<ArrayList<Product>>() {}.type)
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        showToast.value = data
    }

}