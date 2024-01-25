package ru.yandex.slavaach.nullapplicatoin

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.yandex.slavaach.nullapplicatoin.MainActivity
import ru.yandex.slavaach.nullapplicatoin.MarketModule
import ru.yandex.slavaach.nullapplicatoin.services.InAppUpdate
import ru.yandex.slavaach.nullapplicatoin.services.InAppUpdateCallback
import ru.yandex.slavaach.nullapplicatoin.services.fcm.IPushHelper

class RuModule : MarketModule {


    override fun getMapFragment(): Fragment? {
        return null
    }

    override fun getScanFragment(): Fragment? {
        return null
    }

    override fun getPushHelper(): IPushHelper? {
        return null
    }


    override fun getInAppUpdate(
        binding: ViewGroup,
        context: Context,
        activity: MainActivity,
        callback: InAppUpdateCallback,
    ): InAppUpdate? {
        return null
    }
}