package com.joyor.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.adapter.ColorAdapterRv
import com.joyor.adapter.FeatureAdapterRv
import com.joyor.adapter.ProductSliderViewPagerAdapter
import com.joyor.databinding.ActivityProductDetailBinding
import com.joyor.helper.*
import com.joyor.model.Product
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.ProductDetailViewModel
import org.greenrobot.eventbus.EventBus

class ProductDetailActivity : AppCompatActivity() {


    companion object {
        fun newInstance(context: Context, product: Product): Intent {
            return Intent(context, ProductDetailActivity::class.java).putExtra(Constants.product, product)
        }
    }

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: ActivityProductDetailBinding
    private var imagesList: ArrayList<String> = ArrayList()
    private lateinit var progressDialog: CustomProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        progressDialog = CustomProgressBar(this)
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        val product: Product = intent.getParcelableExtra(Constants.product)!!
        viewModel.product.value = product
        binding.viewModel = viewModel
        viewModel.getProductDetails(product.id)
        viewModel.product.observe(this, Observer {
            if (!viewModel.addToCart.value!!) {
                imagesList.clear()
                imagesList.addAll(it.images!!)
                setAdapter()
            }
        })
        viewModel.totalCount.observe(this, Observer {
            binding.count.text = it.toString()
        })
        viewModel.isBack.observe(this, Observer {
            finish()
        })
        viewModel.colorSelect.observe(this, Observer {
            binding.colorName.text = it?.name
        })
        viewModel.addToCart.observe(this, Observer {
            if (it) {
                showToast(getString(R.string.product_added))
                viewModel.product.value!!.quantity = viewModel.totalCount.value as Int
                viewModel.product.value!!.colorSelect = viewModel.colorSelect.value as Product.Option
                val productSave: Product? = JoyorDb.newInstance(this).productDao().containsProduct(viewModel.product.value!!.id, viewModel.product.value!!.colorSelect.code)
                if (productSave == null)
                    JoyorDb.newInstance(this).productDao().addToCartNotReplace(viewModel.product.value!!)
                else {
                    productSave.quantity = viewModel.product.value!!.quantity + productSave.quantity
                    JoyorDb.newInstance(this).productDao().addToCart(viewModel.product.value!!)
                }
                EventBus.getDefault().postSticky(CartUpdateEvent())
            }
        })
        viewModel.isDescription.observe(this, Observer {
            if (it) {
                binding.description.setTextColor(getColor(R.color.white))
                binding.description.background = ContextCompat.getDrawable(this, R.drawable.round_border_brown)
                binding.specification.setTextColor(getColor(R.color.black))
                binding.specification.background = ContextCompat.getDrawable(this, R.drawable.border_product_tab)
                binding.descriptionView.visibility = View.VISIBLE
                binding.featureView.visibility = View.GONE

            } else {
                binding.specification.setTextColor(getColor(R.color.white))
                binding.specification.background = ContextCompat.getDrawable(this, R.drawable.round_border_brown)
                binding.description.setTextColor(getColor(R.color.black))
                binding.description.background = ContextCompat.getDrawable(this, R.drawable.border_product_tab)
                binding.descriptionView.visibility = View.GONE
                binding.featureView.visibility = View.VISIBLE
            }
        })
        viewModel.product.observe(this, Observer {
            if (viewModel.product.value!!.variations?.options?.size!! > 0) {
                binding.colorList.adapter = ColorAdapterRv(viewModel.product.value!!.variations?.options, viewModel)
                viewModel.onColorSelect(viewModel.product.value!!.variations?.options!![0])
            }
        })
        viewModel.progressBar.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else
                progressDialog.dismiss()
        })
    }

    private fun setAdapter() {
        binding.topSliderVp.adapter = ProductSliderViewPagerAdapter(this, imagesList)
        binding.topSliderIndicator.setViewPager(binding.topSliderVp)

        if (viewModel.product.value?.specs?.size!! > 1) {
            binding.divider1.visibility = View.VISIBLE
            binding.divider2.visibility = View.VISIBLE
        }
        binding.featureList.adapter = FeatureAdapterRv(viewModel.product.value?.specs!!)
        binding.featureList.addItemDecoration(
            HorizantalMidDivider(
                ContextCompat.getDrawable(
                    this, R.drawable.divider
                )
            )
        )
        if (viewModel.product.value!!.variations?.options?.size!! > 0) {
            binding.colorList.adapter = ColorAdapterRv(viewModel.product.value!!.variations?.options, viewModel)
            viewModel.onColorSelect(viewModel.product.value!!.variations?.options!![0])
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}