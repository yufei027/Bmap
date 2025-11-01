package com.example.bmap

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class DarkThemeMode(){
    Dark,
    Light,
    System
}

// 继承ViewModel
class DarkThemeViewModel : ViewModel() {
    val themeOptions = listOf(DarkThemeMode.Light, DarkThemeMode.Dark)

    var isSystemMode by mutableStateOf(true)
        private set

    private val _currentThemeMode = MutableStateFlow(DarkThemeMode.System)
    var currentThemeMode: StateFlow<DarkThemeMode> = _currentThemeMode  // 只读 Flow，UI 可以用 collectAsState() 来订阅



    // 切换是否跟随系统
    fun toggleSystemMode(enabled: Boolean) {
        Log.d("DarkModeViewModel", "现在是${enabled}")
        isSystemMode = enabled
        if (enabled) {
            _currentThemeMode.value = DarkThemeMode.System
        } else {
            // 关闭跟随系统，切换到手动模式
            _currentThemeMode.value = DarkThemeMode.Dark
        }
    }

    // 设置手动主题
    fun setThemeMode(mode: DarkThemeMode) {
        Log.d("DarkModeViewModel", "已手动设置为${mode}模式")
        _currentThemeMode.value = mode
    }
}