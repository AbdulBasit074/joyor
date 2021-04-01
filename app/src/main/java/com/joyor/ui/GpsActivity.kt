package com.joyor.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.model.LatLng
import com.joyor.R
import com.joyor.databinding.ActivityGpsBinding
import com.joyor.helper.*
import com.joyor.viewmodel.GpsViewModel

class GpsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGpsBinding
    private lateinit var viewModel: GpsViewModel
    private var yourLocationRq: Int = 1122
    private var yourDestinationRq: Int = 3122
    private var latLngYl: LatLng? = null
    private var latLngDl: LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gps)
        viewModel = ViewModelProviders.of(this).get(GpsViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.isStartClick.observe(this, Observer {
            if (it) {
                if (latLngYl != null && latLngDl != null)
                    moveTo(GoogleMapRouteActivity.newInstance(this, latLngYl!!, latLngDl!!))
                else
                    showToast(getString(R.string.both_field_required))
            }
        })
        viewModel.isBack.observe(this, Observer {
            finish()
        })
        viewModel.ylName.observe(this, Observer {
            binding.yourLocation.text = it
        })
        viewModel.dlName.observe(this, Observer {
            binding.destination.text = it
        })
        viewModel.isYlClick.observe(this, Observer {
            moveForResult(MapActivity::class.java, yourLocationRq)
        })
        viewModel.isDlClick.observe(this, Observer {
            moveForResult(MapActivity::class.java, yourDestinationRq)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                yourLocationRq -> {
                    latLngYl = data?.getParcelableExtra(Constants.dataPassKey)
                    if (latLngYl != null) {
                        viewModel.ylName.value = Constants.geoCoding(latLngYl!!.latitude, latLngYl!!.longitude, this)
                    }
                }
                yourDestinationRq -> {
                    latLngDl = data?.getParcelableExtra(Constants.dataPassKey)
                    if (latLngDl != null) {
                        viewModel.dlName.value = Constants.geoCoding(latLngDl!!.latitude, latLngDl!!.longitude, this)
                    }
                }
            }
        }


    }
}