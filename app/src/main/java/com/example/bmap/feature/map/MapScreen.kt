package com.example.bmap.feature.map
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bmap.feature.components.BottomAppBar
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@Composable
// 记得传入navController
fun MapScreen(navController: NavController){
    Scaffold(
        bottomBar = {
            BottomAppBar(navController)
        }
    ) { innerPadding ->
        Box(
            // innerPadding ，为了自动避开顶部底部栏区域
            modifier = Modifier.padding(innerPadding)
        ) {
            MapboxMap(
                Modifier.fillMaxSize(),
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(2.0)
                        center(Point.fromLngLat(-98.0, 39.5))
                        pitch(0.0)
                        bearing(0.0)
                    }
                }
            )
        }
    }
}




@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MapScreenPreview(){
    val navController = rememberNavController()
    MapScreen(navController)
}