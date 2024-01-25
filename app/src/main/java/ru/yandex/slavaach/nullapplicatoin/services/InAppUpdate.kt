package ru.yandex.slavaach.nullapplicatoin.services

interface InAppUpdate {

    fun checkForUpdates()
    fun startUpdate() {}
}

interface InAppUpdateCallback {
    fun finishInstall()
}
