package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.adapter.DealerAdapterRv
import com.joyor.databinding.LocationFragmentBinding
import com.joyor.helper.HorizontalDecoration
import com.joyor.viewmodel.LocationViewModel


class LocationFragment : Fragment() {


    private lateinit var binding: LocationFragmentBinding
    private lateinit var viewModel: LocationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.location_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        setProductAdapter()
    }

    private fun setProductAdapter() {
        binding.dealerList.adapter = DealerAdapterRv(viewModel.listDummy)
        binding.dealerList.addItemDecoration(HorizontalDecoration())

    }
}