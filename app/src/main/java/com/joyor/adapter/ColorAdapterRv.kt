package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiColorBinding
import com.joyor.model.Product
import com.joyor.viewmodel.ProductDetailViewModel

class ColorAdapterRv(private val items: ArrayList<Product.Option>?, private val viewModel: ProductDetailViewModel) :
    RecyclerView.Adapter<ColorAdapterRv.ViewHolder>() {

    private var itemSelectPositionIs: Int = 0


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

            if (viewModel.colorSelect.value?.name.equals(item.name)!!) {
                binding.layoutColor.setCardBackgroundColor(binding.color.context.getColor(R.color.black))
            } else
                binding.layoutColor.setCardBackgroundColor(binding.color.context.getColor(R.color.transparent))

            binding.layoutColor.setOnClickListener {
                viewModel.onColorSelect(item)
                notifyDataSetChanged()
            }

        }
    }
}

