package com.joyor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiDealerBinding
import com.joyor.model.Store
import com.joyor.viewmodel.DealerViewModel
import java.util.*
import kotlin.collections.ArrayList


class DealerAdapterRv(private val items: ArrayList<Store>, private val viewModel: DealerViewModel) :
    RecyclerView.Adapter<DealerAdapterRv.ViewHolder>(), Filterable {
    companion object {
        var filterStoreList = ArrayList<Store>()
    }

    init {
        filterStoreList = items
    }

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
        viewModel.searchList.value = filterStoreList
        return filterStoreList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(filterStoreList[position])
    }

    inner class ViewHolder(val binding: LiDealerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Store) {
            binding.modelStore = item
            binding.viewModel = viewModel
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterStoreList = if (charSearch.isEmpty()) {
                    items
                } else {
                    val resultList = ArrayList<Store>()
                    for (row in items) {
                        if (row.title?.toLowerCase(Locale.ROOT)!!.contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterStoreList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterStoreList = results!!.values as ArrayList<Store>
                notifyDataSetChanged()
            }

        }
    }


}

