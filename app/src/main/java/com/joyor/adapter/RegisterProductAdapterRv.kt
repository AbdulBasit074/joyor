package com.joyor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiRegisterProductBinding
import com.joyor.model.RegisterProduct
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.RegisterProductViewModel

class RegisterProductAdapterRv(
    private val context: Context, private var items: ArrayList<RegisterProduct>,
    private val viewModel: RegisterProductViewModel
) :
    RecyclerView.Adapter<RegisterProductAdapterRv.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_register_product,
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

    inner class ViewHolder(val binding: LiRegisterProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: RegisterProduct) {
            binding.modelData = item
            binding.deleteBtn.setOnClickListener {
                viewModel.onDeleteRegisterProduct(item.id) { deleteFromItem(adapterPosition, item) }
            }
        }

        private fun deleteFromItem(adapterPosition: Int, item: RegisterProduct) {
            binding.parentView.close(true)
            JoyorDb.newInstance(context).registerProduct().removeProduct(item.id)
            items.removeAt(adapterPosition)
            notifyDataSetChanged()
            viewModel.productListGet.value = true
        }

    }
}

