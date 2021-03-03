package com.joyor.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.joyor.helper.HorizontalDecoration
import com.joyor.model.Store
import com.joyor.viewmodel.LocationViewModel


class LocationFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding: LocationFragmentBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var map: MapView
    private var storeList: ArrayList<Store> = ArrayList()
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
        binding.viewMOdel = viewModel
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.webSiteUrl.observe(requireActivity(), Observer {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        })

        viewModel.storeListing.observe(requireActivity(), Observer {
            storeList.removeAll(storeList)
            storeList.addAll(it)
            binding.dealerList.adapter?.notifyDataSetChanged()
        })

        setProductAdapter()
    }

    private fun setProductAdapter() {
        binding.dealerList.adapter = DealerAdapterRv(storeList, viewModel)
        binding.dealerList.addItemDecoration(HorizontalDecoration())
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        MapsInitializer.initialize(context)
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        for (i in 0 until storeList.size) {
            googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(storeList[i].lat!!, storeList[i].lng!!))
                    .title(storeList[i].title)
            )
        }
    }
}