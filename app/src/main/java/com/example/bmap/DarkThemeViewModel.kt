package com.example.bmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmap.ui.theme.DarkThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// 继承ViewModel
class DarkThemeViewModel(
    private val settingDataRepository: SettingDataRepository
) : ViewModel() {


    // 公开的只读的 StateFlow，供UI层观察。stateIn可以把冷flow转换成热flow
    val darkThemeMode: StateFlow<DarkThemeMode> = settingDataRepository.darkThemeModeFlow.stateIn(
        scope = viewModelScope, //ViewModel 生命周期绑定，ViewModel 被销毁时，viewModelScope也取消，也就停止监听datastore变化
        started = SharingStarted.WhileSubscribed(5000), // 如果五秒内没有人collect，就关闭（取消flow运行）
        initialValue = DarkThemeMode.System // 默认的选项，防止数据未读取期间出现问题
    )


    // 接收参数，修改成对应的深色模式。
    // 在launch中调用，也就是这个设置（写入）操作是在后台进程中完成的
    fun setThemeMode(mode: DarkThemeMode) {
        viewModelScope.launch {
            settingDataRepository.setThemeMode(mode)
        }
    }
}