package com.example.bmap.feature.amap

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.model.MyLocationStyle.LOCATION_TYPE_LOCATE
import com.example.bmap.R
import com.example.bmap.ui.theme.BmapTheme

@Composable
fun MapScreen(){
    val context = LocalContext.current
    // aMap: 用于持有从 AMapView 回调出来的地图控制器对象。初始为 null。
    var aMap by remember { mutableStateOf<AMap?>(null) }

    // 用户是否已授予定位权限
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                hasLocationPermission = true // 用户同意，更新状态
                Toast.makeText(context, "权限已获取", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "未授予定位权限，地图定位功能无法使用", Toast.LENGTH_LONG).show()
            }
        }
    )

    // LaunchedEffect 在 Composable 首次进入屏幕时执行一次。
    // 检查权限，如果尚未获取，就启动权限请求流程。
    LaunchedEffect(key1 = true) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                // 点击时，移动到当前所在的位置
                onClick = {
                    aMap?.let{ map -> // aMap是状态控制器，可为null（只有在 AMapView 的 onMapLoaded 回调被触发后赋值。例如在加载完成前就点击）
                        map.myLocation?.let { location -> // location是位置数据，也可能是null（例如GPS原因没获取到位置）
                            val currentLocation = LatLng(location.latitude, location.longitude) //获取定位位置
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 16f) // 相机移动：中心点和缩放级别

                            map.animateCamera(cameraUpdate) // 移动
                        }

                    }
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.filled_my_location_24),
                    contentDescription = "地图移动到当前位置",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (hasLocationPermission) {
                AMapView( // 调用之前的AMapView组件
                    onMapLoaded = { map -> // 地图加载完成好后，显示地图
                        aMap = map
                        configureMap(map)
                    }
                )
            }
        }
    }

}

/**
 * 集中配置 AMap 对象。
 */
private fun configureMap(map: AMap) {
    val myLocationStyle = MyLocationStyle()

    map.myLocationStyle =  myLocationStyle
        .myLocationType(LOCATION_TYPE_LOCATE)
        .interval(2000)

    map.uiSettings.isMyLocationButtonEnabled = false // 隐藏SDK自带的定位按钮
    map.isMyLocationEnabled = true
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    BmapTheme {
        MapScreen()
    }
}