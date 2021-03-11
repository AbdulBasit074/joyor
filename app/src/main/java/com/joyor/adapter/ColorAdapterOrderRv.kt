package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiColorBinding
import com.joyor.model.Product
import java.security.cert.PKIXRevocationChecker

class ColorAdapterOrderRv(private val items: ArrayList<Product.Option>?, val product: Product, private val onColorChange: (Product.Option) -> Unit) :
    RecyclerView.Adapter<ColorAdapterOrderRv.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_color,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items!![position])
    }

    inner class ViewHolder(val binding: LiColorBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bindView(item: Product.Option) {
            binding.model = item

            if ((product.colorSelect.name == item.name)) {
                binding.layoutColor.setCardBackgroundColor(binding.color.context.getColor(R.color.black))
            } else
                binding.layoutColor.setCardBackgroundColor(binding.color.context.getColor(R.color.transparent))
            binding.layoutColor.setOnClickListener { onColorChange(item) }
        }
    }
}

