package com.joyor.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joyor.helper.Constants
import com.joyor.helper.Persister
import com.joyor.model.*
import com.joyor.model.room.JoyorDb
import com.joyor.service.Results
import com.joyor.service.register.RegisterService

class RegisterProductViewModel : ViewModel(), Results {


    var showToast: MutableLiveData<String> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()
    var back: MutableLiveData<Boolean> = MutableLiveData()
    var registerProductList: MutableLiveData<ArrayList<RegisterProduct>> = MutableLiveData()
    var addProduct: MutableLiveData<Boolean> = MutableLiveData()
    var showProgress: MutableLiveData<Boolean> = MutableLiveData()
    var productListGet: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var context: Context
    private val registerProductListRq: Int = 2221
    private val deleteRegisterProductRq: Int = 1123
    private lateinit var deleteCallBack: () -> Unit


    var updateRegisterProductList: MutableLiveData<Boolean> = MutableLiveData()

    init {
        registerProductList.value = ArrayList()
    }

    fun onDeleteRegisterProduct(id: String, callBackDelete: () -> Unit) {
        deleteCallBack = callBackDelete
        onShowProgress()
        RegisterService(deleteRegisterProductRq, this).deleteRegisterProduct(id, user.value?.iD)
    }

    private fun onShowProgress() {
        showProgress.value = true
    }

    private fun onDismissProgress() {
        showProgress.value = false
    }

    fun getRegisterProducts() {
        onShowProgress()
        RegisterService(registerProductListRq, this).getRegisterProduct(user.value?.iD)
    }

    fun addProduct() {
        addProduct.value = true
    }

    fun onBack() {
        back.value = true
    }

    override fun onSuccess(requestCode: Int, data: String) {
        when (requestCode) {
            registerProductListRq -> {
                onDismissProgress()
                val productRegisterArrayList: ArrayList<RegisterProduct> = Gson().fromJson(data, object : TypeToken<ArrayList<RegisterProduct>>() {}.type)
                if (productRegisterArrayList.size > 0) {
                    JoyorDb.newInstance(context).registerProduct().removeAll()
                    JoyorDb.newInstance(context).registerProduct().addRegisterProduct(productRegisterArrayList)
                    Persister(context).persist(Constants.userProductRegister, true)
                } else
                    Persister(context).persist(Constants.userProductRegister, false)
                productListGet.value = true
            }
            deleteRegisterProductRq -> {
                onDismissProgress()
                deleteCallBack()
            }
        }
    }

    override fun onFailure(requestCode: Int, data: String) {
        onDismissProgress()
        showToast.value = data
    }
}
