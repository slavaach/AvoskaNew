package ru.yandex.slavaach.nullapplicatoin.services

import timber.log.Timber

class AppUnhandledExceptionHandler(private val teg: String) : Thread.UncaughtExceptionHandler {
    private val defaultHandler: Thread.UncaughtExceptionHandler

    init {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    }

    private fun clearSaticData() {
        Timber.tag("error7777").v("Очистка кэша $teg")
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Timber.tag("error7777").v(formatException(e))
        clearSaticData()
        defaultHandler.uncaughtException(t, e)
    }

    fun formatException(e: Throwable?): String {
        val exLogText = StringBuilder()
        var exNode = e
        while (exNode != null) {
            val exHeader = String.format(
                "Ex type: %s Message:%s \n",
                exNode
                    .javaClass.name,
                exNode.message
            )
            exLogText.append(exHeader)
            exLogText.append("Stack trace:\n")
            exLogText.append(getStackTraceText(exNode))
            exNode = exNode.cause
        }
        return exLogText.toString()
    }

    private fun getStackTraceText(e: Throwable): String? {
        val stackTraceText = java.lang.StringBuilder()
        for (elem in e.stackTrace) {
            stackTraceText.append(elem.toString()).append("\n")
        }
        return stackTraceText.toString()
    }
}
