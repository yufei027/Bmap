package com.example.bmap.feature.map

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.bmap.R

@Composable
fun FloatingSearchBar(){
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { newText:String ->
            text = newText},
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.Serching_destination),
            )
        },
        keyboardOptions = KeyboardOptions( // 设置键盘按钮为“搜索”
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions( // 键盘上的搜索被点击时触发
            onSearch = { // 执行搜索 TODO
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = stringResource(R.string.clear_input),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = TextFieldDefaults.shape, //RoundedCornerShape(16.dp)
        colors = TextFieldDefaults.colors(
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface
        )
    )
}