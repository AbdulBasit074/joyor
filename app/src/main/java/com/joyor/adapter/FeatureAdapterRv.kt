package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiFaqBinding
import com.joyor.databinding.LiFeatureBinding
import com.joyor.model.Product

class FeatureAdapterRv(private val items: ArrayList<Product.Image>) :
    RecyclerView.Adapter<FeatureAdapterRv.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_feature,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    inner class ViewHolder(val binding: LiFeatureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Product.Image) {
        }
    }
}

