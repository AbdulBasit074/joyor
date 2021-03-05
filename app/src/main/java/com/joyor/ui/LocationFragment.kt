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
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.joyor.R
import com.joyor.adapter.DealerAdapterRv
import com.joyor.databinding.LocationFragmentBinding
import com.joyor.helper.CustomProgressBar
import com.joyor.helper.HorizontalDecoration
import com.joyor.model.Store
import com.joyor.viewmodel.LocationViewModel


class LocationFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding: LocationFragmentBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var adapter: DealerAdapterRv
    private lateinit var progressDialog: CustomProgressBar

    private var storeList: ArrayList<Store> = ArrayList()
    private  var gMap: GoogleMap? = null
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
        progressDialog = CustomProgressBar(requireContext())
        binding.viewMOdel = viewModel
        adapter = DealerAdapterRv(storeList, viewModel)
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel.progressBar.observe(requireActivity(), Observer {
            if(it)
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
            gMap!!.clear()
            var focus = 0f
            focus = if(store.size>1)
                10f
            else
                15f
            store.forEach {
                val markerOrigin = MarkerOptions().position(LatLng(it.lat!!, it.lng!!))
                gMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.lat!!, it.lng!!), focus))
                gMap!!.addMarker(markerOrigin)
            }
        }

    }

    private fun setProductAdapter() {
        binding.dealerList.adapter = adapter
        binding.dealerList.addItemDecoration(HorizontalDecoration())
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        gMap = googleMap!!
    }
}

