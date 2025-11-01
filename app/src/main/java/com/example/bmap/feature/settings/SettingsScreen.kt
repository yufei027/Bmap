package com.example.bmap.feature.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bmap.feature.components.BottomAppBar
import com.example.bmap.navigation.Screen
import com.example.bmap.ui.theme.BmapTheme





@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    Log.d("SettingsScreen", "SettingsScreen 被渲染")
    Scaffold(
        topBar = { },
        bottomBar = { BottomAppBar(navController)},
        snackbarHost = { },
        modifier = Modifier

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp) //原来可以写多个padding
        ) {
            Spacer(modifier = Modifier.padding(32.dp))
            SettingsGroup(
                entries = listOf(
                    SettingEntry(
                        "深色模式",
                        onClick = { navController.navigate(Screen.SettingDarkModeScreen.route) }),
                    SettingEntry(
                        "主题颜色",
                        onClick = { navController.navigate(Screen.SettingThemeScreen.route) }),
                    SettingEntry(
                        "语言",
                        onClick = { navController.navigate(Screen.SettingLanguageScreen.route) })
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SettingsGroup(
                entries = listOf(
                    SettingEntry(
                        "登录",
                        onClick = { navController.navigate(Screen.LoginScreen.route) })
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsPreview() {
    val navController = rememberNavController() // 假的
    BmapTheme(darkTheme = isSystemInDarkTheme()) {
        SettingsScreen(
            navController = navController
        )
    }
}

