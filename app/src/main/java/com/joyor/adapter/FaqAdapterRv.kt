package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiFaqBinding
import com.joyor.databinding.LiSettingBinding
import com.joyor.model.Faq
import com.joyor.model.Setting
import com.joyor.viewmodel.SettingViewModel

class FaqAdapterRv(private val items: ArrayList<Faq>) :
    RecyclerView.Adapter<FaqAdapterRv.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_faq,
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

    inner class ViewHolder(val binding: LiFaqBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Faq) {
            binding.model = item
        }
    }
}

