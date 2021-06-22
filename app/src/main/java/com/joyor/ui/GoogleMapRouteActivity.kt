package com.joyor.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.joyor.R
import com.joyor.databinding.ActivityGoogleMapRouteBinding
import com.joyor.helper.Constants
import com.joyor.helper.getCurrentLocation
import com.joyor.helper.setLanguage
import com.joyor.service.Results
import com.joyor.service.google.GoogleService
import com.joyor.viewmodel.RouteViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.json.JSONObject


class GoogleMapRouteActivity : AppCompatActivity(), OnMapReadyCallback, Results {

    private lateinit var binding: ActivityGoogleMapRouteBinding
    private var origin: LatLng? = null
    private lateinit var map: GoogleMap
    private var directionRequest: Int = 2231
    private var destination: LatLng? = null
    private var currentLocationMarker: Marker? = null
    private lateinit var viewModel: RouteViewModel

    companion object {
        fun newInstance(context: Context, destination: LatLng, origin: LatLng): Intent {
            return Intent(context, GoogleMapRouteActivity::class.java).putExtra(Constants.destination, destination).putExtra(Constants.origin, origin)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_map_route)
        origin = intent.getParcelableExtra(Constants.origin)!!
        destination = intent.getParcelableExtra(Constants.destination)!!
        viewModel = ViewModelProviders.of(this).get(RouteViewModel::class.java)
        viewModel.back.observe(this, Observer {
            finish()
        })
        binding.viewModel = viewModel
        val mapView: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        map.setPadding(100, 100, 100, 100)
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        if (origin != null && destination != null) {
            GoogleService(directionRequest, this).getDirection(matchLatLan(origin!!), matchLatLan(destination!!), false, "walking", getString(R.string.google_maps_api))
        }
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
                    locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000L, 0f) {
                        currentLocationMarker?.position = LatLng(it.latitude, it.longitude)
                    }
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
    }

    private fun matchLatLan(latLan: LatLng): String {
        return latLan.latitude.toString() + "," + latLan.longitude.toString()
    }

    override fun onSuccess(requestCode: Int, data: String) {
        var jsonObj = JSONObject(data)
        val routesArray = jsonObj.getJSONArray("routes")
        jsonObj = routesArray.get(0) as JSONObject
        jsonObj = jsonObj.getJSONObject("overview_polyline")
        val polyLine = jsonObj.getString("points")
        if (polyLine.isNotEmpty())
            checkLatLanList(polyLine)
    }

    private fun checkLatLanList(polyLine: String) {
        val patternGapPixel = 10
        val dot: PatternItem = Dot()
        val gap: PatternItem = Gap(patternGapPixel.toFloat())

        val polyLines = PolylineOptions()
            .addAll(PolyUtil.decode(polyLine))
            .width(20f)
            .color(getColor(R.color.color_map))
            .startCap(RoundCap())
            .endCap(RoundCap())
        val patternDotted: List<PatternItem> = listOf(gap, dot)
        val markerDestination = MarkerOptions().position(destination!!)
        map.addMarker(markerDestination)
        val markerOrigin = MarkerOptions().position(origin!!).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_current_location_marker))
        currentLocationMarker?.remove()
        currentLocationMarker = map.addMarker(markerOrigin)
        polyLines.pattern(patternDotted)
        map.addPolyline(polyLines)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 17f))

        val builder = LatLngBounds.Builder()
        builder.include(destination)
        builder.include(origin)
        val bounds = builder.build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 140))
    }

    override fun onFailure(requestCode: Int, data: String) {
    }


}