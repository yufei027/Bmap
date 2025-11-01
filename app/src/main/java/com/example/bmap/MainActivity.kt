package com.example.bmap
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.bmap.feature.map.MapScreen
import com.example.bmap.feature.settings.DarkModeSettingScreen
import com.example.bmap.feature.settings.SettingsScreen
import com.example.bmap.navigation.AppNavHost
import com.example.bmap.ui.theme.BmapTheme
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

public class MainActivity : ComponentActivity() {
    lateinit var permissionsManager: PermissionsManager

    val permissionsListener: PermissionsListener = object : PermissionsListener {
        // 当需要向用户解释为什么请求权限时调用
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            Toast.makeText(this@MainActivity, "我们使用定位权限来显示位置，如果不授予权限，我们则不会定位", Toast.LENGTH_SHORT).show()
        }
        // 当用户做出同意或拒绝的决定时调用
        override fun onPermissionResult(granted: Boolean) {
            if (granted) { // 如果允许权限则启用用户定位
                //enableLocationComponent()
            } else { //如果拒绝则不用定位，只显示地图

            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PermissionsManager.areLocationPermissionsGranted(this@MainActivity)) {
            // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
        } else {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this@MainActivity)
        }
        setContent {
            Log.d("MapActivity", "setContent 开始执行")
            val navController = rememberNavController()

            val viewModel: DarkThemeViewModel = viewModel()
            val currentThemeMode by viewModel.currentThemeMode.collectAsState()
            val useDarkTheme = when (currentThemeMode) {
                DarkThemeMode.System -> isSystemInDarkTheme() // 跟随系统
                DarkThemeMode.Dark -> true
                DarkThemeMode.Light -> false
            }
            BmapTheme(
                darkTheme = useDarkTheme
            ) {
                AppNavHost(
                    navController = navController,
                    viewModel = viewModel,
                )
            }

        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    val navController = rememberNavController()
    MapScreen(navController)
}