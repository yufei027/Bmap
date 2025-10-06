package com.example.bmap

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bmap.navigation.Screen

@Composable
fun BottomAppBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        NavigationBar {
        NavigationBarItem(
            selected = (currentRoute == Screen.MapScreen.route),
            onClick = {
                navController.navigate(Screen.MapScreen.route) {
                    launchSingleTop = true // 如果顶部已经是这个页面，就不要重复显示
                    restoreState = true // 记住离开时的页面位置，返回时回到这个位置
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = stringResource(R.string.map),
                    modifier = modifier
                )
            },
            label = {
                Text(text = stringResource(R.string.map))
            }
        )
        NavigationBarItem(
            selected = (currentRoute == Screen.FavoriteScreen.route),
            onClick = {
                navController.navigate(Screen.FavoriteScreen.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = stringResource(R.string.favorites),
                    modifier = modifier
                )
            },
            label = {
                Text(text = stringResource(R.string.favorites))
            }
        )
        NavigationBarItem(
            selected = (currentRoute == Screen.SettingScreen.route),
            onClick = {
                navController.navigate(Screen.SettingScreen.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(R.string.setting),
                    modifier = modifier
                )
            },
            label = {
                Text(text = stringResource(R.string.setting))
            }
        )
    }
}
