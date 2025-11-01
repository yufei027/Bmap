package com.example.bmap.navigation


sealed class Screen(val route: String) {
    object SettingScreen: Screen("setting_screen")
    object MapScreen: Screen("map_screen") // Screen 的一个子类型，把 route 属性赋值为 "main_screen"
    object FavoriteScreen: Screen("favorite_screen")
    object SettingDarkModeScreen: Screen("setting_dark_mode_screen")
    object LoginScreen: Screen("login_screen")
    object SettingThemeScreen: Screen("setting_Theme_screen")
    object SettingLanguageScreen: Screen("setting_language_screen")
}