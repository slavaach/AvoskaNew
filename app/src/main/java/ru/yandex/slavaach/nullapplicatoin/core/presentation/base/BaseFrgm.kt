package ru.yandex.slavaach.nullapplicatoin.core.presentation.base

import timber.log.Timber

abstract class BaseFrgm {

    init {
        Timber.tag("7777").v("${javaClass.simpleName} onInit")
    }
}
