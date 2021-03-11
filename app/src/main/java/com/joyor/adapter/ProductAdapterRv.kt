package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiProductBinding
import com.joyor.model.Product
import com.joyor.viewmodel.ProductViewModel

class ProductAdapterRv(private val items: ArrayList<Product>, private val viewModel: ProductViewModel) :
    RecyclerView.Adapter<ProductAdapterRv.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_product,
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

    inner class ViewHolder(val binding: LiProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Product) {
            binding.model = item
            binding.viewModel = viewModel
            binding.productFeature.adapter = FeatureProductMainRv(item.specs)
        }
    }


}

