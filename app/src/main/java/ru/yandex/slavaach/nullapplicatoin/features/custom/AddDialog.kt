package ru.yandex.slavaach.nullapplicatoin.features.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.core.theme.pulsateClick
import ru.yandex.slavaach.nullapplicatoin.core.theme.scaledSp

@Composable
fun AddDialog(viewModelCustom: CustomViewModel) {
    val state by remember { viewModelCustom.state }

    var expandedHome = remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = {
            viewModelCustom.adding()
        },
        title = {
            Text(
                text = state.titleDialog,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
        },
        text = {
            Column() {
                Text(
                    text = "",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.height(5.dp)
                )
                TextField(
                    value = state.nameForAddOrUpdate,
                    onValueChange = {
                        viewModelCustom.setNameForAddOrUpdate(it)
                    },
                    textStyle = TextStyle(fontSize = 22.sp, color = MaterialTheme.colors.secondary),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box() {
                    Text(
                        text = if (viewModelCustom.state.value.nameSale.isNullOrBlank()) {
                            "Надо что-то выбрать"
                        } else {
                            viewModelCustom.state.value.nameSale
                        },
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable {
                            expandedHome.value = true
                        }
                    )
                    AddDialogDropdownMenu(viewModelCustom, expandedHome)
                }
            }
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .pulsateClick()
                        .weight(1f)
                        .padding(8.dp),
                    onClick = { viewModelCustom.adding() },
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.cancel_item),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.secondary,
                    )
                }
                Button(
                    modifier = Modifier
                        .pulsateClick()
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
                        viewModelCustom.add()
                        viewModelCustom.adding()
                    },
                ) {
                    Text(
                        text = if (viewModelCustom.openAddOrUpdate) {
                            LocalContext.current.getString(R.string.add_item_button_ok)
                        } else {
                            LocalContext.current.getString(R.string.update_item_button_ok)
                        },
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.secondary,
                        overflow = TextOverflow.Clip,
                        softWrap = false
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
    )
}

@Composable
fun AddDialogDropdownMenu(viewModelCustom: CustomViewModel, expandedHome: MutableState<Boolean>) {
    val stateList = viewModelCustom.stateSale.collectAsState()
    val allList = stateList.value.list
    DropdownMenu(
        expanded = expandedHome.value,
        onDismissRequest = { expandedHome.value = false },
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .border(BorderStroke(width = 1.dp, color = MaterialTheme.colors.surface))
            .shadow(elevation = 2.dp)
            .width(200.dp)
            .padding(4.dp),
        // modifier = Modifier.padding(12.dp) // .border(2.dp, Color.Red)
    ) {
        val heightDropdownMenu = (20.scaledSp + 24.dp) * allList.size + 16.dp
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .height(heightDropdownMenu)
                .width(500.dp)
        ) {
            items(allList.size) { position ->
                val item = allList.get(position)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            expandedHome.value = false
                            viewModelCustom.setSale(item.id, item.name)
                        }
                ) {
                    Text(
                        text = if (item.name.isNullOrBlank()) "Надо что-то выбрать" else item.name,
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.secondary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
