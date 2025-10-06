package com.example.bmap.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.bmap.BottomAppBar
import com.example.bmap.R
import com.example.bmap.ui.theme.BmapTheme




data class SettingEntry(
    val title: String,
    val onClick: () -> Unit
)
@Composable
fun SettingItem(
        title: String,
        onClick: () -> Unit,
        enabled: Boolean = true,
        modifier: Modifier = Modifier,
        trailingContent:  @Composable (() -> Unit)? = null
    ) {
    val textColor = if (enabled) {MaterialTheme.colorScheme.onSurface} else {MaterialTheme.colorScheme.onSurface.copy(0.38f)}
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            //.height(56.dp)
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClickLabel = stringResource(R.string.enter_selection), //这个就像是contentDescription一样
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),

    ) {
        //Icon()
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,

        )
        Spacer(modifier = Modifier.weight(1f))
        if (trailingContent != null) {
            // 如果调用者提供了内容，就显示它
            trailingContent()
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.enter),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SettingsGroup(
    entries: List<SettingEntry>, // 由多个组合起来的列表
    modifier: Modifier = Modifier,
    trailingContent: @Composable (() -> Unit)? = null
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column {
            entries.forEachIndexed { index, item -> // 遍历这个列表
                SettingItem(title = item.title, onClick = item.onClick, trailingContent = trailingContent)
                if (index != entries.lastIndex) { // 如果不是最后一个，就插入分割线
                    HorizontalDivider(
                        thickness = DividerDefaults.Thickness,
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onNavigateToDarkMode: () -> Unit,
    onNavigateToTheme: () -> Unit,
    onNavigateToLanguage: () -> Unit,
    onNavigateToLogIn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.padding(32.dp))
        SettingsGroup(
            entries = listOf(
                SettingEntry("深色模式") { onNavigateToDarkMode() },
                SettingEntry("主题颜色") { onNavigateToTheme() },
                SettingEntry("语言") { onNavigateToLanguage() }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        SettingsGroup(
            entries = listOf(
                SettingEntry("登录") { onNavigateToLogIn() },
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsPreview() {
    val navController = rememberNavController() // 假的
    BmapTheme {
        Scaffold(
            topBar = { },
            bottomBar = { BottomAppBar(navController)},
            floatingActionButton = { }, // 悬浮按钮
            floatingActionButtonPosition = FabPosition.End,
            snackbarHost = { }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                SettingsScreen(
                    onNavigateToDarkMode = {},
                    onNavigateToTheme = {},
                    onNavigateToLanguage = {},
                    onNavigateToLogIn = {},
                )
            }
        }
    }
}

