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
import com.joyor.helper.moveTo
import com.joyor.helper.moveToAndFinish
import com.joyor.model.Setting
import com.joyor.viewmodel.SettingViewModel

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel
    private var settingListUpdated: ArrayList<Setting> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        binding.viewModel = viewModel
        setAdapter()

        viewModel.settingListing.observe(this, Observer {
            settingListUpdated.removeAll(settingListUpdated)
            settingListUpdated.addAll(it)
            binding.settingList.adapter!!.notifyDataSetChanged()
        })
        viewModel.openFAQ.observe(this, Observer {
            moveTo(FaqActivity::class.java)
        })
        viewModel.webSiteUrl.observe(this, Observer {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        })
    }
    private fun setAdapter() {
        binding.settingList.adapter = SettingAdapterRv(settingListUpdated, viewModel)
    }

}