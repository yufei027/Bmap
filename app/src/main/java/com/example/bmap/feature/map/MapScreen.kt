package com.example.bmap.feature.map
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.example.bmap.R
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.bmap.BottomAppBar
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

@Composable
// 记得传入navController
fun MapScreen(navController: NavController){
    Scaffold(
        bottomBar = {
            BottomAppBar(navController)
        }
    ) { innerPadding ->
        Box(
            // innerPadding 会自动避开底部栏区域
            modifier = Modifier.padding(innerPadding)
        ) {
            val mapViewportState = rememberMapViewportState()
            MapboxMap(
                Modifier.fillMaxSize(),
                // 缩放级别为 2，美国为中心(-98.0, 39.5)，没有倾斜pitch和旋转bearing
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(2.0)
                        center(Point.fromLngLat(-98.0, 39.5))
                        pitch(0.0)
                        bearing(0.0)
                    }
                },
            ){
                MapEffect(Unit) { mapView ->
                    // 设置地图上的带方向的 2D 小蓝点
                    mapView.location.updateSettings {
                        locationPuck = createDefault2DPuck(withBearing = true)
                        enabled = true
                        puckBearing = PuckBearing.COURSE
                        puckBearingEnabled = true
                        // pulsingEnabled = true
                    }
                    // 地图中心会自动移动到用户位置
                    mapViewportState.transitionToFollowPuckState()
                }
            }
        }


    }
}


@Composable
fun FloatingSearchBar(){
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { newText:String ->
            text = newText},
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.Serching_destination),
                )
        },
        keyboardOptions = KeyboardOptions( // 设置键盘按钮为“搜索”
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions( // 键盘上的搜索被点击时触发
            onSearch = { // 执行搜索 TODO
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = stringResource(R.string.clear_input),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = TextFieldDefaults.shape, //RoundedCornerShape(16.dp)
        colors = TextFieldDefaults.colors(
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface
        )
    )
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MapScreenPreview(){

}