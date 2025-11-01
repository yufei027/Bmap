package com.example.bmap.feature.settings

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmap.DarkThemeMode
import com.example.bmap.DarkThemeViewModel
import com.example.bmap.R
import com.example.bmap.ui.theme.BmapTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DarkModeSettingScreen(
    onBack: () -> Unit,
    viewModel: DarkThemeViewModel = viewModel()
) {


    val isSystemMode = viewModel.isSystemMode
    val themeOptions = viewModel.themeOptions
    val currentMode by viewModel.currentThemeMode.collectAsState()

    val isSystemDark = isSystemInDarkTheme()
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.Darkmode)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.return_to_previous_page)
                        )
                    }
                },
                actions = {} //这里是右侧操作区
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ){
            Surface(
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 2.dp,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingsGroup(
                    entries = listOf(
                        SettingEntry(title = stringResource(R.string.follow_system), onClick = {})
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Switch(
                        checked = isSystemMode, // 显示状态
                        modifier = Modifier,
                        onCheckedChange = { enabled -> // 为 true 时，表示跟随系统
                            viewModel.toggleSystemMode(enabled) // onThemeChange()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))

            Surface(
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 2.dp,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.fillMaxWidth()
            ){
                Column {
                    themeOptions.forEachIndexed { index, mode ->
                        SettingItem(
                            title = if (mode == DarkThemeMode.Light) "浅色模式" else "深色模式",
                            enabled = !isSystemMode,// 仅在非系统模式下可点击，下面 enabled 同理
                            onClick = { // 点击时，即变为对应颜色模式
                                if (!isSystemMode) {
                                    Log.d("DarkModeViewModel", "_currentThemeMode.value: $currentMode")
                                    viewModel.setThemeMode(mode)
                                    Log.d("DarkModeViewModel", "_currentThemeMode.value: $currentMode")
                                }
                            }
                        ) {
                            val selected = if (isSystemMode) {
                                // 跟随系统时，选中状态由系统深色判断
                                (isSystemDark && mode == DarkThemeMode.Dark) || (!isSystemDark && mode == DarkThemeMode.Light)
                            } else {
                                // 手动模式时，选中状态由 currentMode 决定
                                currentMode == mode
                            }  // 先不用这个
                            RadioButton(
                                // 当“跟随系统”关闭，并且选中“浅色模式”时，浅色模式按钮亮
                                selected = (!isSystemMode) && (currentMode == mode),
                                onClick = null,
                                enabled = !isSystemMode,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledSelectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                                    disabledUnselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                )
                            )
                        }
                        if (index != themeOptions.lastIndex) HorizontalDivider(thickness = DividerDefaults.Thickness)
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
    BmapTheme(darkTheme = isSystemInDarkTheme()) {
        DarkModeSettingScreen(
            onBack = {}
        )
    }
}


