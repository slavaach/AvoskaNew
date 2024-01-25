package ru.yandex.slavaach.nullapplicatoin.core.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.update(m: T.() -> T) {
    this.value?.let {
        this.value = m.invoke(it)
    }
}

fun <T> MutableLiveData<T>.post(m: T.() -> T) {
    this.value?.let {
        this.postValue(m.invoke(it))
    }
}

fun <T> LiveData<T>.requireValue(): T {
    return value!!
}
