package com.joyor.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.joyor.R
import com.joyor.databinding.ActivityAddressOnMapBinding
import com.joyor.helper.Constants
import com.joyor.helper.askToEnableGPS
import com.joyor.helper.getCurrentLocation
import com.joyor.helper.setLanguage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val searchPlaceRc = 1
    private var mMap: GoogleMap? = null
    private var fields = arrayListOf(Place.Field.LAT_LNG, Place.Field.NAME)
    private var latLng: LatLng? = null
    private var isGpsEnabled = false
    private lateinit var mBinding: ActivityAddressOnMapBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_address_on_map)
        initView()
    }

    private fun initView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
        if (!isGpsEnabled)
            askToEnableGPS { requestCode, resultCode, data -> onActivityResult(requestCode, resultCode, data) }
        mBinding.searchEtContainer.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(this)
            startActivityForResult(intent, searchPlaceRc)
        }
        mBinding.back.setOnClickListener {
            finish()
        }
        mBinding.confirmBtn.setOnClickListener {
            val intent = Intent()
            if (latLng != null) {
                intent.putExtra(Constants.dataPassKey, latLng)
                setResult(RESULT_OK, intent)
            }
            onBackPressed()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        if (isGpsEnabled|| latLng == null) {
            Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        getCurrentLocation(fields) { place -> onLocationAvailable(place) }
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        if (response.isPermanentlyDenied) {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
        } else {
            if(latLng!=null)
                 mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        }
        mMap?.setOnCameraIdleListener {
            mBinding.markerPinElevated.visibility = View.VISIBLE
            mBinding.markerPin.visibility = View.GONE
            latLng = mMap?.cameraPosition?.target
        }
        mMap?.setOnCameraMoveStartedListener {
            mBinding.markerPin.visibility = View.VISIBLE
            mBinding.markerPinElevated.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.locationDialogRequestKey && resultCode == Activity.RESULT_OK) {
            isGpsEnabled = true
            onMapReady(mMap)
        } else if (requestCode == searchPlaceRc && resultCode == RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                latLng = place.latLng
                mMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                mBinding.searchTv.text = place.name
            }
        }
    }

    private fun onLocationAvailable(place: Place) {
        latLng = LatLng(place.latLng!!.latitude, place.latLng!!.longitude)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL

    }
}