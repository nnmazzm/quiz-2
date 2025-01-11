package com.example.mobileapplications22.models

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency

data class User(
    val uid : String,
    val avatar : String ?= null
){
    constructor(): this("","")
}
