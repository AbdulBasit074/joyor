package com.joyor.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.joyor.R
import com.joyor.adapter.ProductAdapterRv
import com.joyor.databinding.FragmentProductBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.HorizontalDoubleItemDecoration
import com.joyor.helper.moveTo
import com.joyor.model.Product
import com.joyor.viewmodel.ProductViewModel


class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var progressDialog: CustomProgressBar
    private var productList: ArrayList<Product> = ArrayList()
    private lateinit var adapter: ProductAdapterRv
    private var categoryList: HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        progressDialog = CustomProgressBar(requireContext())
        viewModel.context = requireContext()
        viewModel.getProducts()
        binding.viewModel = viewModel
        setTabListeners()
        viewModel.productListing.observe(requireActivity(), Observer {
            productList.clear()
            productList.addAll(it)
            binding.productList.adapter?.notifyDataSetChanged()
        })

        viewModel.product.observe(requireActivity(), Observer {
            moveTo(ProductDetailActivity.newInstance(requireActivity(), it))
        })

        viewModel.progressBar.observe(requireActivity(), Observer {
            if (it) {
                progressDialog.show()
            } else
                progressDialog.dismiss()
        })
        setProductAdapter()
    }

    private fun setTabListeners() {
        binding.scootersTab.setOnClickListener {
            resetTabs()
            binding.scootersTab.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_reddish))
            binding.scootersIv.setImageResource(R.drawable.ic_scooters_on)
            binding.scootersTv.setTextColor(Color.WHITE)
            adapter.filter.filter(getString(R.string.joyor_scooters))
        }
        binding.accessoriesTab.setOnClickListener {
            resetTabs()
            binding.accessoriesTab.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_reddish))
            binding.accessoriesIv.setImageResource(R.drawable.ic_scooters_on)
            binding.accessoriesTv.setTextColor(Color.WHITE)
            adapter.filter.filter(getString(R.string.accessories))
        }
        binding.sparePartsTab.setOnClickListener {
            resetTabs()
            binding.sparePartsTab.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_reddish))
            binding.sparePartsIv.setImageResource(R.drawable.ic_scooters_on)
            binding.sparePartsTv.setTextColor(Color.WHITE)
            adapter.filter.filter(getString(R.string.joyor_spare_parts))
        }
    }

    private fun resetTabs() {
        binding.scootersTab.setBackgroundColor(Color.WHITE)
        binding.scootersIv.setImageResource(R.drawable.ic_scooters_off)
        binding.scootersTv.setTextColor(Color.BLACK)
        binding.accessoriesTab.setBackgroundColor(Color.WHITE)
        binding.accessoriesIv.setImageResource(R.drawable.ic_accessories_off)
        binding.accessoriesTv.setTextColor(Color.BLACK)
        binding.sparePartsTab.setBackgroundColor(Color.WHITE)
        binding.sparePartsIv.setImageResource(R.drawable.ic_spare_parts_off)
        binding.sparePartsTv.setTextColor(Color.BLACK)
    }

    private fun setProductAdapter() {
        adapter = ProductAdapterRv(requireContext(), productList, viewModel)
        binding.productList.layoutManager = GridLayoutManager(activity, 2)
        binding.productList.addItemDecoration(HorizontalDoubleItemDecoration())
        binding.productList.adapter = adapter
    }

}