package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
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
        setSpinnerValues()
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

    private fun setSpinnerValues() {
        val adapterSpinner: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.filter_list))
        adapterSpinner.setDropDownViewResource(R.layout.li_drop_down)
        binding.spinner.adapter = adapterSpinner
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapter.filter.filter(resources.getStringArray(R.array.filter_list)[position].toString())
            }
        }
    }
    private fun setProductAdapter() {
        adapter = ProductAdapterRv(requireContext(), productList, viewModel)
        binding.productList.layoutManager = GridLayoutManager(activity, 2)
        binding.productList.addItemDecoration(HorizontalDoubleItemDecoration())
        binding.productList.adapter = adapter
    }

}