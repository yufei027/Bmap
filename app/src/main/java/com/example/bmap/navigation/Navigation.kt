package com.example.bmap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bmap.feature.amap.MapScreen
import com.example.bmap.feature.settings.DarkModeSettingScreen
import com.example.bmap.feature.settings.SettingsScreen
import com.example.bmap.ui.theme.DarkThemeMode

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    darkThemeMode: DarkThemeMode,
    onThemeChange: (DarkThemeMode) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screen.MapScreen.route,
        modifier = Modifier
    ){
        composable(route = Screen.MapScreen.route){
            MapScreen()
        }
        composable(route = Screen.SettingScreen.route) {
            SettingsScreen(
                { navController.navigate(route = Screen.DarkModeScreen.route) },
                { navController.navigate(route = Screen.DarkModeScreen.route) },
                { navController.navigate(route = Screen.DarkModeScreen.route) },
                { navController.navigate(route = Screen.DarkModeScreen.route) }
            )
        }
        composable(
            route = Screen.DarkModeScreen.route,
            //arguments =
        ) {
            DarkModeSettingScreen(
                darkThemeMode = darkThemeMode,
                onThemeChange = onThemeChange
            )
        }
    }
}