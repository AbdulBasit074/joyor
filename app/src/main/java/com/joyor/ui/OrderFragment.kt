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
import com.joyor.adapter.OrderAdapterRv
import com.joyor.databinding.OrderFragmentBinding
import com.joyor.helper.HorizontalDoubleItemDecoration
import com.joyor.viewmodel.OrderViewModel

class OrderFragment : Fragment() {


    private lateinit var binding: OrderFragmentBinding
    private lateinit var viewModel: OrderViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        setProductAdapter()
    }

    private fun setProductAdapter() {
        binding.orderList.layoutManager = GridLayoutManager(activity, 2)
        binding.orderList.adapter = OrderAdapterRv(viewModel.listDummy)
        binding.orderList.addItemDecoration(HorizontalDoubleItemDecoration())

    }

}