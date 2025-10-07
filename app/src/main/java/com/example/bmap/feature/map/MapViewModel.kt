package com.example.bmap.feature.map

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.Location
import com.mapbox.common.location.LocationObserver
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel : ViewModel() {
    private var locationProvider: DeviceLocationProvider? = null
    private val locationService : LocationService = LocationServiceFactory.getOrCreate()
    private val locationObserver = object: LocationObserver { // 实现LocationObserver接口
        override fun onLocationUpdateReceived(locations: MutableList<Location>) {
            Log.e("debugLog", "Location update received: $locations")
        }
    }

    // 获取 locationProvider：
    private fun initLocationProvider(context: Context) {

        val request = LocationProviderRequest.Builder()
                        .interval(IntervalSettings.Builder().interval(0L).minimumInterval(0L).maximumInterval(0L).build())
                        .displacement(0F)
                        .accuracy(AccuracyLevel.HIGHEST)
                        .build();

        val result = locationService.getDeviceLocationProvider(request)

        if (result.isValue) {
            locationProvider = result.value!! // 安全检查，通过了才赋值
        } else {
            Log.e("debugLog", "Failed to get device location provider")
        }
    }

    // 接受位置更新：创建一个 LocationObserver 并覆盖 onLocationUpdateReceived 函数
    private fun receiveLocationUpdates() {
        locationProvider?.addLocationObserver(locationObserver)
    }

    override fun onCleared() {
        super.onCleared()
        locationProvider?.removeLocationObserver(locationObserver)
    }
}