package com.example.bmap.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmap.ui.theme.BmapTheme
import com.example.bmap.ui.theme.DarkThemeMode

@Composable
fun DarkModeSettingScreen(
    darkThemeMode:DarkThemeMode,
    onThemeChange: (DarkThemeMode) -> Unit
) {


    val isSystemMode = (darkThemeMode == DarkThemeMode.System) //当前模式和系统模式一样，就为true
    val isDarkMode = (darkThemeMode == DarkThemeMode.Dark)

    val themeOptions = listOf("浅色模式", "深色模式")
    val selectedThemeOption = when (darkThemeMode) {
        DarkThemeMode.Light -> "浅色模式"
        DarkThemeMode.Dark -> "深色模式"
        else -> "" // 跟随系统时，没有手动选择
    }
    val isSystemCurrentlyDark = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ){
        Spacer(modifier = Modifier.padding(32.dp))
        SettingsGroup(
            entries = listOf(
                SettingEntry(title = "深色模式跟随系统") {}
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Switch(
                checked = isSystemMode, // 显示状态
                modifier = Modifier,
                onCheckedChange = { isChecked -> // isChecked 为 true 时，表示打开开关，表示跟随系统
                    val newMode =
                        if (isChecked) DarkThemeMode.System else if (isSystemCurrentlyDark) DarkThemeMode.Dark else DarkThemeMode.Light
                    onThemeChange(newMode)
                }
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            modifier = Modifier.fillMaxWidth()
        ){
            Column() {
                themeOptions.forEachIndexed { index, theme ->
                    val currentOptionMode = if (theme == "浅色模式") DarkThemeMode.Light else DarkThemeMode.Dark
                    SettingItem(
                        title = theme,
                        onClick = {
                            // 仅在非系统模式下可点击，下面 enabled 同理
                            if (!isSystemMode) {
                                onThemeChange(currentOptionMode)
                            }
                        },
                        enabled = !isSystemMode
                    ) {
                        RadioButton(
                            selected = !isSystemMode && (theme == selectedThemeOption),
                            onClick = null,
                            enabled = !isSystemMode,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledSelectedColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.38f
                                ),
                                disabledUnselectedColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.12f
                                )

                            )
                        )
                    }
                    if (index != themeOptions.lastIndex) {
                        HorizontalDivider(
                            thickness = DividerDefaults.Thickness,
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DarkModeSettingsPreview() {
    BmapTheme {
        Scaffold(
            bottomBar = {
                //BottomAppBar()
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                DarkModeSettingScreen(DarkThemeMode.Light){}
            }
        }
    }
}

