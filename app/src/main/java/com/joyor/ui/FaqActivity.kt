package com.joyor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.joyor.R
import com.joyor.adapter.FaqAdapterRv
import com.joyor.adapter.SettingAdapterRv
import com.joyor.databinding.ActivityFaqBinding
import com.joyor.model.Faq
import com.joyor.viewmodel.FaqViewModel

class FaqActivity : AppCompatActivity() {

    private var faqListing: ArrayList<Faq> = ArrayList()
    private lateinit var binding: ActivityFaqBinding
    private lateinit var viewModel: FaqViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)
        viewModel = ViewModelProviders.of(this).get(FaqViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.back.observe(this, Observer {
            finish()
        })
        viewModel.faqListing.observe(this, Observer {
            faqListing.clear()
            faqListing.addAll(it)
            binding.faqList.adapter?.notifyDataSetChanged()
        })
        setAdapter()
    }

    private fun setAdapter() {
        binding.faqList.adapter = FaqAdapterRv(faqListing)
    }
}