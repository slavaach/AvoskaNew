package ru.yandex.slavaach.nullapplicatoin

import android.content.Context
import java.lang.ref.WeakReference

class ActivityContextHolder {
    var contextRef: WeakReference<Context>? = null
}

class MainViewModelHolder {
    var viewModelRef: WeakReference<MainViewModel>? = null
}
