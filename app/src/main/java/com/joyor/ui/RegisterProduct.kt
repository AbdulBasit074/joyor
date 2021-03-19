package com.joyor.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.adapter.RegisterProductAdapterRv
import com.joyor.databinding.ActivityRegisterProductBinding
import com.joyor.helper.*
import com.joyor.model.RegisterProduct
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.RegisterProductViewModel

class RegisterProduct : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterProductBinding
    private lateinit var viewModel: RegisterProductViewModel
    private lateinit var loading: CustomProgressBar
    private var registerProductRequest: Int = 882


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_product)
        loading = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(RegisterProductViewModel::class.java)
        viewModel.user.value = JoyorDb.newInstance(this).userDao().getLoggedUser()
        viewModel.registerProductList.value = JoyorDb.newInstance(this).registerProduct().getAllRegisterProduct() as ArrayList<RegisterProduct>
        checkViewVisibility()
        viewModel.context = this
        viewModel.addProduct.observe(this, Observer {
            moveForResult(RegisterNewProduct::class.java, registerProductRequest)
        })
        viewModel.back.observe(this, Observer {
            if (it)
                finish()
        })
        viewModel.showProgress.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })
        viewModel.updateRegisterProductList.observe(this, Observer {
            binding.productRegister.adapter?.notifyDataSetChanged()
        })
        viewModel.productListGet.observe(this, Observer {
            viewModel.registerProductList.value!!.clear()
            viewModel.registerProductList.value!!.addAll(JoyorDb.newInstance(this).registerProduct().getAllRegisterProduct() as ArrayList<RegisterProduct>)
            checkViewVisibility()
        })
        setAdapter()
        binding.viewModel = viewModel
    }

    private fun checkViewVisibility() {
        if (viewModel.registerProductList.value?.size!! > 0) {
            binding.noRegister.visibility = View.GONE
            binding.registerTitle.visibility = View.VISIBLE
            binding.productRegister.visibility = View.VISIBLE
            binding.productRegister.adapter?.notifyDataSetChanged()
        } else {
            binding.noRegister.visibility = View.VISIBLE
            binding.registerTitle.visibility = View.GONE
            binding.productRegister.visibility = View.GONE
            Persister(this).persist(Constants.userProductRegister, false)
        }
    }

    private fun setAdapter() {
        binding.productRegister.adapter = RegisterProductAdapterRv(this, viewModel.registerProductList.value!!, viewModel)
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                registerProductRequest -> {
                    viewModel.getRegisterProducts()
                }
            }
        }


    }
}