package ru.yandex.slavaach.nullapplicatoin.core.presentation

import ru.yandex.slavaach.nullapplicatoin.R
import ru.yandex.slavaach.nullapplicatoin.core.domain.ErrorEntity

class ErrorResolver constructor(
    private val alertManager: AlertManager,
) {

    fun callError(value: ErrorEntity) {
        when (value) {
            is ErrorEntity.Unknown -> alertManager.showAlert(
                Alert.Error(
                    title = R.string.error_title,
                    text = "Даже не знаю что сказать",
                    textOkButton = R.string.error_button,
                )
            )
            is ErrorEntity.ErrorWithAlertString -> alertManager.showAlert(
                Alert.Error(
                    title = R.string.error_title,
                    text = value.alert,
                    textOkButton = R.string.error_button,
                )
            )
        }
    }
}
