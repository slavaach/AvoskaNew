package ru.yandex.slavaach.nullapplicatoin.features.reference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.core.theme.pulsateClick
import timber.log.Timber

@Composable
fun AddDialog(viewModelRef: ReferenceViewModel) {
    val state by remember { viewModelRef.state }
    AlertDialog(
        onDismissRequest = {
            viewModelRef.adding()
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
                        viewModelRef.setNameForAddOrUpdate(it)
                    },
                    textStyle = TextStyle(fontSize = 22.sp, color = MaterialTheme.colors.secondary),
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = if (state.sortForAddOrUpdate == -1) "" else state.sortForAddOrUpdate.toString(),
                    onValueChange = {
                        val toInt = try {
                            it.toInt()
                        } catch (e: Exception) {
                            Timber.tag("7777").e("error = $it")
                            0
                        }
                        viewModelRef.setSortForAddOrUpdate(toInt)
                    },
                    textStyle = TextStyle(fontSize = 22.sp, color = MaterialTheme.colors.secondary),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
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
                    onClick = { viewModelRef.adding() },
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
                        viewModelRef.add()
                        viewModelRef.adding()
                    },
                ) {
                    Text(
                        text = if (viewModelRef.openAddOrUpdate) {
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
