package com.example.bmap.feature.amap

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView

@Composable
fun AMapView(
    onMapLoaded: (AMap) -> Unit
){

    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, key2 = mapView) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    mapView.onCreate(Bundle())
                    Log.d("MyMapDebug", ".onCreate() called.")

                }
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                // 其他事件如 ON_START, ON_STOP 可以按需添加
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            // 在这里执行所有清理工作，这对于防止内存泄漏至关重要！
            // a. 移除我们之前添加的观察者
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            // b. 销毁 MapView 以释放底层资源
            mapView.onDestroy()
        }
    }

    // 3. 使用 AndroidView 将 MapView 集成到 Compose UI 树中
    // AndroidView 是一个桥梁，它允许你在 Compose 布局中嵌入一个传统的 Android View。
    AndroidView(factory = { mapView }) { view ->

        onMapLoaded(view.map)
    }
}