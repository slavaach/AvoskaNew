package ru.yandex.slavaach.nullapplicatoin.core.domain

open class ErrorEntity : Throwable() {

    object Unknown : ErrorEntity()

    class ErrorWithAlertString(val alert: String) : ErrorEntity()
}

interface ErrorHandler {

    fun getError(throwable: Throwable, text: String = ""): ErrorEntity
}
