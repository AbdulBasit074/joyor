package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.helper.Constants
import com.joyor.helper.Persister
import com.joyor.model.Product
import com.joyor.service.Results
import com.joyor.service.store.StoreService

class ProductViewModel : ViewModel(), Results {

    var productListing: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    var product: MutableLiveData<Product> = MutableLiveData()
    var progressBar: MutableLiveData<Boolean> = MutableLiveData()
    var showToast: MutableLiveData<String> = MutableLiveData()
    private val productListRequest = 6623
    lateinit var context: Context
    private fun onShowProgress() {
        progressBar.value = true
    }

    private fun onDismissProgress() {
        progressBar.value = false
    }

    fun onProductClick(productClick: Product) {
        product.value = productClick
    }

   fun getProducts() {
        onShowProgress()
        val productDataPersists = Persister.with(context).getPersisted(Constants.productDataPersists, null)
        if (productDataPersists != null)
            onSuccess(productListRequest, productDataPersists)
        StoreService(productListRequest, this).getProducts()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            productListRequest -> {
                Persister.with(context).persist(Constants.productDataPersists, data)
                productListing.value = Gson().fromJson(data, object : TypeToken<ArrayList<Product>>() {}.type)
                onDismissProgress()
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }

}