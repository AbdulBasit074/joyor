package com.joyor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiCartBinding
import com.joyor.model.Product
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.CartViewModel

class CartAdapterRv(private val context: Context, private val items: ArrayList<Product>, private val viewModel: CartViewModel, private val changeCallBack: () -> Unit) :
    RecyclerView.Adapter<CartAdapterRv.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_cart,
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

    inner class ViewHolder(val binding: LiCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Product) {
            binding.model = item
            binding.minus.setOnClickListener {
                if (item.quantity != 1)
                    item.quantity = item.quantity - 1
                binding.count.text = item.quantity.toString()
                binding.totalAmount.text = (item.price!!.toInt() * item.quantity.toInt()).toString()
                JoyorDb.newInstance(context).productDao().updateProduct(item)

                changeCallBack()
            }
            binding.plus.setOnClickListener {
                item.quantity = item.quantity + 1
                binding.count.text = item.quantity.toString()
                binding.totalAmount.text = (item.price!!.toInt() * item.quantity.toInt()).toString()
                JoyorDb.newInstance(context).productDao().updateProduct(item)
                changeCallBack()
            }
            binding.deleteBtn.setOnClickListener {
                JoyorDb.newInstance(context).productDao().removeProduct(item.id, item.colorSelect.code)
                changeCallBack()
            }
            if (item.variations?.options != null)
                binding.colorRv.adapter = ColorAdapterOrderRv(item.variations?.options, item) { option -> onColorChange(option, item) }
            binding.totalAmount.text = (item.price!!.toInt() * item.quantity.toInt()).toString()
            binding.count.text = item.quantity.toString()
        }

        private fun onColorChange(option: Product.Option, item: Product) {

            if (option.code != item.colorSelect.code) {
                val productSave: Product? = JoyorDb.newInstance(context).productDao().containsProduct(item.id, option.code)
                if (productSave == null) {
                    JoyorDb.newInstance(context).productDao().updateColor(item.colorSelect.code, option.code, option.name, item.id)
                } else {
                    JoyorDb.newInstance(context).productDao().removeProduct(item.id, item.colorSelect.code)
                    productSave.quantity = item.quantity + productSave.quantity
                    JoyorDb.newInstance(context).productDao().updateColorQuantity(productSave.quantity, productSave.colorSelect.code, productSave.id)
                }
                changeCallBack()
            }
        }
    }
}

