package ru.yandex.slavaach.nullapplicatoin.services.fcm

import android.app.Application
import android.content.Context

interface IPushHelper {

    fun initPushes(app: Application)

    fun createNotificationPushChannel(app: Application)

    suspend fun getFCMToken(): String

    fun checkPushAvailability(context: Context)
}
