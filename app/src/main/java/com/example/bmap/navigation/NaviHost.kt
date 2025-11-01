package com.example.bmap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bmap.DarkThemeMode
import com.example.bmap.DarkThemeViewModel
import com.example.bmap.feature.map.MapScreen
import com.example.bmap.feature.settings.DarkModeSettingScreen
import com.example.bmap.feature.settings.SettingsScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: DarkThemeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MapScreen.route,
        modifier = Modifier,

    ){
        // 设置页面
        composable(Screen.SettingScreen.route) {
            SettingsScreen(navController)
        }
        composable(Screen.SettingDarkModeScreen.route) {
            DarkModeSettingScreen(
                onBack = {navController.popBackStack()},
                viewModel = viewModel
            )
        }
        composable(Screen.MapScreen.route){
            MapScreen(navController)
        }
    }

}