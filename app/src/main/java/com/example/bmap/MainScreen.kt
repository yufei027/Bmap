package com.example.bmap

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bmap.navigation.AppNavHost
import com.example.bmap.navigation.Screen
import com.example.bmap.ui.theme.DarkThemeMode

@Composable
fun MainScreen(
    darkThemeMode: DarkThemeMode,
    onThemeChange: (DarkThemeMode) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route // 获得当前路由名字

    // 白名单：显示底部栏
    val screensWithBottomBar = listOf(
        Screen.MapScreen.route,
        Screen.SettingScreen.route,
    )
    Scaffold(
        topBar = {},
        bottomBar = {
            if (currentRoute in screensWithBottomBar) {
                BottomAppBar(navController)
            }
        },
        modifier = Modifier
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            darkThemeMode = darkThemeMode,
            onThemeChange = onThemeChange
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview(){
    MainScreen(DarkThemeMode.Light){}
}