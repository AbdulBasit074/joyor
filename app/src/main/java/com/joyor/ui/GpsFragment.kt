package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.GpsSectionBinding
import com.joyor.viewmodel.GpsViewModel

class GpsFragment : Fragment() {


    private lateinit var binding: GpsSectionBinding
    private lateinit var viewModel: GpsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.gps_section, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GpsViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.isStartClick.observe(this, Observer { it ->

            if (it) {
            }
        })
    }


}