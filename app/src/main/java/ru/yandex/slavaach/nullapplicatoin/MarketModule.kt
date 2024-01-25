package ru.yandex.slavaach.nullapplicatoin

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.yandex.slavaach.nullapplicatoin.services.InAppUpdate
import ru.yandex.slavaach.nullapplicatoin.services.InAppUpdateCallback
import ru.yandex.slavaach.nullapplicatoin.services.fcm.IPushHelper

interface MarketModule {
    fun getMapFragment(): Fragment?

    fun getScanFragment(): Fragment?

    fun getPushHelper(): IPushHelper?

    fun getInAppUpdate(
        binding: ViewGroup,
        context: Context,
        activity: MainActivity,
        callback: InAppUpdateCallback,
    ): InAppUpdate?
}
