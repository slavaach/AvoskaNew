package ru.yandex.slavaach.nullapplicatoin.component.bottomBar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.BaseViewModel
import ru.yandex.slavaach.nullapplicatoin.core.presentation.base.TypeOnClick
import ru.yandex.slavaach.nullapplicatoin.core.theme.scaledSp

@Composable
fun DefaltBottomBar(viewModel: BaseViewModel) {
    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxHeight()) {
        BottomAppBar(backgroundColor = MaterialTheme.colors.primary) {
            IconButton(onClick = {
                viewModel.onClick(OnClickAddDefaltBottomBar("добавить"))
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Добавить",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.scaledSp)
                        .height(24.scaledSp),
                )
            }
            IconButton(onClick = { viewModel.onClick(OnClickUpdateDefaltBottomBar("изменить")) }) {
                Icon(
                    Icons.Filled.Create,
                    contentDescription = "Изменить",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.scaledSp)
                        .height(24.scaledSp),
                )
            }
            IconButton(onClick = {
                viewModel.onClick(OnClickDeleteDefaltBottomBar("удалить"))
            }) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Удалить",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.scaledSp)
                        .height(24.scaledSp),
                )
            }
            Spacer(Modifier.weight(1f, true))
            /*IconButton(onClick = { }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Избранное",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.scaledSp)
                        .height(24.scaledSp),
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.mipmap.orders),
                    contentDescription = "Информация о приложении",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(36.scaledSp)
                        .height(36.scaledSp),
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Filled.FilterList,
                    contentDescription = "Избранное",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .width(24.scaledSp)
                        .height(24.scaledSp),
                )
            }*/
        }
    }
}

data class OnClickAddDefaltBottomBar(val name: String) : TypeOnClick

data class OnClickDeleteDefaltBottomBar(val name: String) : TypeOnClick

data class OnClickUpdateDefaltBottomBar(val name: String) : TypeOnClick
