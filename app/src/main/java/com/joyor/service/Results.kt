package com.joyor.service

interface Results {

    fun onSuccess(requestCode:Int,data:String)
    fun onFailure(requestCode:Int,data:String)



}