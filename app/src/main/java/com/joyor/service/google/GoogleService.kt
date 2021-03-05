package com.joyor.service.google

import android.hardware.Sensor
import com.google.android.datatransport.runtime.Destination
import com.google.android.gms.maps.model.LatLng
import com.joyor.service.BaseService
import com.joyor.service.Results
import com.joyor.service.RetrofitClient

class GoogleService(requestCode: Int, callBack: Results) : BaseService(requestCode, callBack,true) {

    fun getDirection(origin:String,destination: String,sensor: Boolean,mode:String,key:String){
        RetrofitClient.getInstanceForGoogleMap().create(GoogleClient::class.java).getDirection(origin,destination,sensor,mode,key).enqueue(this)
    }

}