package com.joyor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.joyor.R
import com.joyor.databinding.LiSliderProductBinding
import com.joyor.model.Product
import androidx.databinding.DataBindingUtil.inflate

class ProductSliderViewPagerAdapter(private val context: Context, private val sliderImage: ArrayList<Product.Image>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = inflate<LiSliderProductBinding>(LayoutInflater.from(context), R.layout.li_slider_product, container, false)
        Glide.with(binding.sliderImage.context).load(sliderImage[position].src).into(binding.sliderImage)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return sliderImage.size
    }
}
