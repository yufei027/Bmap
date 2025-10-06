package com.example.bmap

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.bmap.ui.theme.DarkThemeMode
import kotlinx.coroutines.flow.map

// 使用属性委托在 Context 的顶层创建一个 DataStore 实例
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")


class SettingDataRepository(private val context: Context) {

    // 创建 key，用于在 DataStore 中存储和检索主题设置，把所有 Key 放在一个私有的 companion object 中
    private companion object{
        val DARK_THEME_MODE_KEY = stringPreferencesKey("dark_theme_mode")
        val COLOR_THEME_MODE_KEY = stringPreferencesKey("color_theme_mode")
        val LANGUAGE_MODE_KEY = stringPreferencesKey("language_mode")

    }

    // 创建一个 Flow，用于实时监听“深色模式”的设置。
    val darkThemeModeFlow = context.dataStore.data
        .map { preferences ->
            // 从 preferences 中读取字符串，如果不存在（比如用户第一次打开App），则默认值为 System
            val modeName = preferences[DARK_THEME_MODE_KEY] ?: DarkThemeMode.System.name

            // 将字符串转换回class  直接使用内置的 valueOf()
            DarkThemeMode.valueOf(modeName)

        }

    // suspend 函数来保存新的主题设置
    suspend fun setThemeMode(mode: DarkThemeMode) {
        context.dataStore.edit { settings ->
            // 将名称（作为字符串）存入 DataStore
            // 如果对于sealed class object 不要用.toString()，object 的 .toString() 方法会包含包名和内存地址hash

            settings[DARK_THEME_MODE_KEY] = mode.name
        }
    }
}