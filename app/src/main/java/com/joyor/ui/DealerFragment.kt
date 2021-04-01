package com.joyor.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.joyor.R
import com.joyor.adapter.DealerAdapterRv
import com.joyor.databinding.DealerFragmentBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.HorizontalDecoration
import com.joyor.model.Store
import com.joyor.viewmodel.DealerViewModel


class DealerFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding: DealerFragmentBinding
    private lateinit var viewModel: DealerViewModel
    private lateinit var adapter: DealerAdapterRv
    private lateinit var progressDialog: CustomProgressBar

    private var storeList: ArrayList<Store> = ArrayList()
    private var gMap: GoogleMap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.dealer_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DealerViewModel::class.java)
        progressDialog = CustomProgressBar(requireContext())
        viewModel.context = requireContext()
        viewModel.getStoreList()
        binding.viewMOdel = viewModel
        adapter = DealerAdapterRv(storeList, viewModel)
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel.progressBar.observe(requireActivity(), Observer {
            if (it)
                progressDialog.show()
            else
                progressDialog.dismiss()
        })
        viewModel.webSiteUrl.observe(requireActivity(), Observer {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        })
        binding.searchEt.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        viewModel.storeListing.observe(requireActivity(), Observer {
            storeList.clear()
            storeList.addAll(it)
            updateMarker(it)
            binding.dealerList.adapter?.notifyDataSetChanged()
        })
        viewModel.searchList.observe(requireActivity(), Observer {
            updateMarker(it)
        })
        setProductAdapter()
    }

    private fun updateMarker(store: ArrayList<Store>) {
        if (gMap != null) {

            val builder = LatLngBounds.Builder()
            gMap!!.clear()
            store.forEach {
                val markerOrigin = MarkerOptions().position(LatLng(it.lat!!, it.lng!!))
                builder.include(markerOrigin.position)
                gMap!!.addMarker(markerOrigin)
            }

            if (store.size > 0) {
                val bounds = builder.build()
                val cu = CameraUpdateFactory.newLatLngBounds(bounds, 10)
                gMap!!.animateCamera(cu)
            }
        }
    }

    private fun setProductAdapter() {
        binding.dealerList.adapter = adapter
        binding.dealerList.addItemDecoration(HorizontalDecoration())
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        gMap = googleMap!!
                    gMap!!.setPadding(100, 100, 100, 100)

    }
}

