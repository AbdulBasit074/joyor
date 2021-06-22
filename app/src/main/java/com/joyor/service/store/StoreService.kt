package com.joyor.service.store

import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient


class StoreService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack) {


    fun getStore() {
        RetrofitClient.getInstance().create(StoreClient::class.java).getStore().enqueue(this)
    }

    fun getProducts() {
        RetrofitClient.getInstance().create(StoreClient::class.java).getProducts().enqueue(this)
    }

    fun getProductDetails(productId: Int?) {
        RetrofitClient.getInstance().create(StoreClient::class.java).getProductDetails(productId).enqueue(this)
    }


}