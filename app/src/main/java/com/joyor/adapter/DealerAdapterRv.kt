package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiDealerBinding
import com.joyor.databinding.LiOrderBinding
import com.joyor.databinding.LiProductBinding

class DealerAdapterRv(private val items:ArrayList<String>) :
    RecyclerView.Adapter<DealerAdapterRv.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.li_dealer,
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
    inner class ViewHolder(val binding: LiDealerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: String) {
        }
    }


}
