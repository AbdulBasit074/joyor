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
import com.joyor.helper.Constants
import com.joyor.helper.HorizantalMidDivider
import com.joyor.model.Product
import com.joyor.viewmodel.ProductDetailViewModel

class ProductDetailActivity : AppCompatActivity() {


    companion object {
        fun newInstance(context: Context, product: Product): Intent {
            return Intent(context, ProductDetailActivity::class.java).putExtra(Constants.product, product)
        }
    }

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var binding: ActivityProductDetailBinding
    private var imagesList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        viewModel.product.value = intent.getParcelableExtra(Constants.product)
        binding.viewModel = viewModel

        viewModel.product.observe(this, Observer {
            imagesList.clear()
            imagesList.addAll(it.images!!)
            setAdapter()
        })
        viewModel.totalCount.observe(this, Observer {
            binding.count.text = it.toString()
        })

        viewModel.isBack.observe(this, Observer {
            finish()
        })
        viewModel.colorSelect.observe(this, Observer {
            binding.colorName.text = it
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
        if (viewModel.product.value!!.variations?.options != null) {
            binding.colorList.adapter = ColorAdapterRv(viewModel.product.value!!.variations?.options as ArrayList<Product.Variations.Option>?, viewModel)

            viewModel.onColorSelect(viewModel.product.value!!.variations?.options!![0].name!!)
        }

    }
}