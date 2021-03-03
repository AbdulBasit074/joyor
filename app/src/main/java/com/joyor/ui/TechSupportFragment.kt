package com.joyor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.databinding.LoginLayoutBinding
import com.joyor.databinding.TechSupportFragmentBinding
import com.joyor.helper.moveToAndFinish
import com.joyor.helper.showToast
import com.joyor.model.room.JoyorDb
import com.joyor.viewmodel.LoginSignUpViewModel
import com.joyor.viewmodel.TechSupportViewModel


class TechSupportFragment : Fragment() {


    private lateinit var binding: TechSupportFragmentBinding
    private lateinit var viewModel: TechSupportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.tech_support_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TechSupportViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.showToast.observe(requireActivity(), Observer {
            showToast(it)
        })

    }


}

