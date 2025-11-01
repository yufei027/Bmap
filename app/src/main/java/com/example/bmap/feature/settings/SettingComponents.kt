package com.example.bmap.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.bmap.R

data class SettingEntry(
    val title: String,
    val onClick: () -> Unit,

)

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    trailingContent:  @Composable (() -> Unit)? = null // 尾部内容
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
        shadowElevation = 2.dp,
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
