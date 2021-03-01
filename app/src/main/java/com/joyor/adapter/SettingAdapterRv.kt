package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiSettingBinding
import com.joyor.model.Setting
import com.joyor.viewmodel.SettingViewModel

class SettingAdapterRv(private val items: ArrayList<Setting>, private val viewModel: SettingViewModel) :
    RecyclerView.Adapter<SettingAdapterRv.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_setting,
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

    inner class ViewHolder(val binding: LiSettingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Setting) {
            binding.model = item
            binding.viewModel = viewModel
        }
    }


}

