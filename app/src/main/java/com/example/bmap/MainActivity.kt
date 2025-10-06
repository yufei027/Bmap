package com.example.bmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmap.ui.theme.BmapTheme
import com.example.bmap.ui.theme.DarkThemeMode
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingDataRepository = SettingDataRepository(applicationContext)

        enableEdgeToEdge()
        setContent {
            // 创建一个 ViewModelFactory 来告诉系统如何创建带参数的 ViewModel
            val darkThemeViewModel: DarkThemeViewModel = viewModel(
                factory = object : ViewModelProvider.Factory { // 一个临时的对象，创建一个DarkThemeViewModel 类型的 ViewModel
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return DarkThemeViewModel(settingDataRepository) as T
                    }
                }
            )
            //val darkThemeViewModel: DarkThemeViewModel = viewModel()
            val darkThemeMode by darkThemeViewModel.darkThemeMode.collectAsState()

            val useDarkTheme = when (darkThemeMode) {
                DarkThemeMode.System -> isSystemInDarkTheme()
                DarkThemeMode.Light -> false
                DarkThemeMode.Dark -> true

            }
            BmapTheme(
                darkTheme = useDarkTheme
            ) {
                MainScreen(
                    //需要传递当前状态和一个改变的lambda
                    darkThemeMode = darkThemeMode,
                    onThemeChange = { newMode: DarkThemeMode ->      // 传递一个改变状态的 Lambda
                        darkThemeViewModel.setThemeMode(newMode)
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BmapTheme {
        MainScreen(DarkThemeMode.Light){}
    }
}