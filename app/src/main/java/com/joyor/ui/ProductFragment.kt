package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.joyor.R
import com.joyor.adapter.ProductAdapterRv
import com.joyor.databinding.FragmentProductBinding
import com.joyor.helper.HorizontalDoubleItemDecoration
import com.joyor.viewmodel.ProductViewModel


class ProductFragment : Fragment() {


    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        binding
        setProductAdapter()
    }

    private fun setProductAdapter() {
        binding.productList.layoutManager = GridLayoutManager(activity, 2)
        binding.productList.adapter = ProductAdapterRv(viewModel.listDummy)
        binding.productList.addItemDecoration(HorizontalDoubleItemDecoration())
    }

}