package com.joyor.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.adapter.SettingAdapterRv
import com.joyor.databinding.FragmentSettingBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.moveTo
import com.joyor.model.Setting
import com.joyor.viewmodel.SettingViewModel

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel
    private var settingListUpdated: ArrayList<Setting> = ArrayList()
    private lateinit var progressDialog: CustomProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        progressDialog = CustomProgressBar(requireContext())
        viewModel.context = requireContext()
        binding.viewModel = viewModel


        viewModel.settingListing.observe(requireActivity(), Observer {
            settingListUpdated.clear()
            settingListUpdated.addAll(it)
            binding.settingList.adapter!!.notifyDataSetChanged()
        })

        viewModel.openFAQ.observe(requireActivity(), Observer {
            moveTo(FaqActivity::class.java)
        })

        viewModel.webSiteUrl.observe(requireActivity(), Observer {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        })

        viewModel.progressBar.observe(requireActivity(), Observer {
            if (it)
                progressDialog.show()
            else
                progressDialog.dismiss()
        })
        setAdapter()
        viewModel.getSettingList()
    }
    private fun setAdapter() {
        binding.settingList.adapter = SettingAdapterRv(settingListUpdated, viewModel)
    }

}