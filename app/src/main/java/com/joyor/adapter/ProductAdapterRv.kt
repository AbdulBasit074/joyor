package com.joyor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.joyor.R
import com.joyor.databinding.LiProductBinding
import com.joyor.model.Product
import com.joyor.model.Store
import com.joyor.viewmodel.ProductViewModel
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapterRv(private val context: Context, private val items: ArrayList<Product>, private val viewModel: ProductViewModel) :
    RecyclerView.Adapter<ProductAdapterRv.ViewHolder>(), Filterable {

    companion object {
        var filterProducts = ArrayList<Product>()
    }

    init {
        filterProducts = items
    }

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
        return filterProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(filterProducts[position])
    }

    inner class ViewHolder(val binding: LiProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Product) {
            binding.model = item
            binding.viewModel = viewModel
            binding.productFeature.adapter = FeatureProductMainRv(item.specs)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val categorySearch = constraint.toString()
                filterProducts = if (categorySearch.isEmpty() || context.getString(R.string.filter) == categorySearch) {
                    items
                } else {

                    val resultList = ArrayList<Product>()
                    for (row in items) {
                        var addInList = false
                        if (row.categories!!.size > 0) {
                            row.categories!!.forEach {
                                if (it.name?.toLowerCase(Locale.ROOT).equals(categorySearch.toLowerCase(Locale.ROOT))) {
                                    addInList = true
                                }
                            }
                        }
                        if (addInList)
                            resultList.add(row)
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterProducts
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterProducts = results?.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }


}

