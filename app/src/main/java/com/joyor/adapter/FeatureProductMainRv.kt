package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiColorBinding
import com.joyor.databinding.LiFeatureRvBinding
import com.joyor.model.Product
import com.joyor.viewmodel.ProductDetailViewModel

class FeatureProductMainRv(private val items: ArrayList<Product.Spec>?) :
    RecyclerView.Adapter<FeatureProductMainRv.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_feature_rv,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (items!!.size < 5) items!!.size else 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items!![position])
    }

    inner class ViewHolder(val binding: LiFeatureRvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Product.Spec) {
            binding.model = item
        }
    }
}

