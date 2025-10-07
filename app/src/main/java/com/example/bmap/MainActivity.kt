package com.example.bmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bmap.feature.map.MapScreen
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

public class MainActivity : ComponentActivity() {
    lateinit var permissionsManager: PermissionsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 检查当前是否已经授予定位权限
        if (PermissionsManager.areLocationPermissionsGranted(this@MainActivity)) {

        } else {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this@MainActivity)
        }

        setContent {
            val navController = rememberNavController()
            MapScreen(navController)
        }
    }

    // 选择允许/拒绝后，执行回调，调用这个函数，将结果交给PermissionsManager
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // 返回权限状态信息的接口，把它传递给PermissionsManager 的构造函数
    var permissionsListener: PermissionsListener = object : PermissionsListener {
        // 解释为什么要请求权限
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            Toast.makeText(this@MainActivity, "需要解释定位权限", Toast.LENGTH_SHORT).show()

        }

        // 当用户同意或拒绝权限时回调
        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
                Toast.makeText(this@MainActivity, "权限已授予", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "权限被拒绝", Toast.LENGTH_SHORT).show()
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