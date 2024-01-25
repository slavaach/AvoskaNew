package ru.yandex.slavaach.nullapplicatoin.core.presentation

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yandex.slavaach.nullapplicatoin.MainViewModel
import ru.yandex.slavaach.nullapplicatoin.MainViewModelHolder
import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.core.theme.pulsateClick

sealed class Alert {
    class Error(
        val title: Int?,
        val text: String?,
        val callbackOk: (() -> Unit)? = null,
        val textOkButton: Int = R.string.error_button,
    ) : Alert()

    class Info(
        val title: Int?,
        val text: String?,
        val callbackOk: (() -> Unit)? = null,
        val callbackNot: (() -> Unit)? = null,
        val textOkButton: Int?,
        val textNotButton: Int?,
    ) : Alert()

    class ErrorHtml(val value: Int) : Alert()

    object NotImplemented : Alert()
}

class AlertManager(
    val mainViewModelHolder: MainViewModelHolder,
) {

    fun showAlert(alert: Alert) {
        when (alert) {
            is Alert.Error -> {
                infoAlert(
                    title = alert.title,
                    text = alert.text,
                    callbackOk = alert.callbackOk,
                    callbackNot = null,
                    textOkButton = alert.textOkButton,
                    textNotButton = null,
                )
            }

            is Alert.Info -> {
                infoAlert(
                    title = alert.title,
                    text = alert.text,
                    callbackOk = alert.callbackOk,
                    callbackNot = alert.callbackNot,
                    textOkButton = alert.textOkButton,
                    textNotButton = alert.textNotButton,
                )
            }

            is Alert.ErrorHtml -> {
            }

            is Alert.NotImplemented -> {
            }

            else -> {}
        }
    }

    private fun infoAlert(
        title: Int?,
        text: String?,
        callbackOk: (() -> Unit)? = null,
        callbackNot: (() -> Unit)? = null,
        textOkButton: Int?,
        textNotButton: Int?,
    ) {
        mainViewModelHolder.viewModelRef?.get()?.state?.let {
            val openAllert = DataForAlert(
                title = title,
                text = text,
                callbackOk = callbackOk,
                callbackNot = callbackNot,
                textOkButton = textOkButton,
                textNotButton = textNotButton,
            )
            it.value = it.value.copy(openAlert = true, dataForAlert = openAllert)
        }
    }
}

@Composable
fun InfoDialog(
    title: Int?,
    text: String?,
    callbackOk: (() -> Unit)? = null,
    callbackNot: (() -> Unit)? = null,
    textOkButton: Int?,
    textNotButton: Int?,
    viewModel: MainViewModel,
    contex: Context,
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.clouseAllert()
        },
        title = {
            Text(
                text = contex.getString(title ?: R.string.empty) ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
        },
        text = {
            Text(
                text = text ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                if (textNotButton != null) {
                    Button(
                        modifier = Modifier
                            .pulsateClick()
                            .weight(1f)
                            .padding(8.dp),
                        onClick = {
                            callbackNot?.invoke()
                            viewModel.clouseAllert()
                        },
                    ) {
                        Text(
                            text = contex.getString(textNotButton) ?: "",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.secondary,
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .pulsateClick()
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
                        callbackOk?.invoke()
                        viewModel.clouseAllert()
                    },
                ) {
                    Text(
                        text = contex.getString(textOkButton ?: R.string.empty) ?: "",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
    )
}

data class DataForAlert(
    val title: Int?,
    val text: String?,
    val callbackOk: (() -> Unit)? = null,
    val callbackNot: (() -> Unit)? = null,
    val textOkButton: Int?,
    val textNotButton: Int?,
)
