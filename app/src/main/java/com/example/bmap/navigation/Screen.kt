package com.example.bmap.navigation


sealed class Screen(val route: String) {
    object MapScreen: Screen("map_screen") // Screen 的一个子类型，把 route 属性赋值为 "main_screen"
    object FavoriteScreen: Screen("favorite_screen")
    object SettingScreen: Screen("setting_screen")
    object DarkModeScreen: Screen("dark_mode_screen")
}