package ru.yandex.slavaach.nullapplicatoin.features.custom.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yandex.slavaach.nullapplicatoin.db.dao.SettingHomeDao
import ru.yandex.slavaach.nullapplicatoin.db.entity.SettingHome

class SettingUseCase(private val settingHomeDao: SettingHomeDao,) {

    suspend fun update(home: Long) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val setting = settingHomeDao.find()
            settingHomeDao.add(listOf(SettingHome(0, home, setting?.idBuyHome ?: listOf())))
        }
    }

    suspend fun update(home: List<Long>) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val setting = settingHomeDao.find()
            settingHomeDao.add(listOf(SettingHome(0, setting?.idHome ?: 0, home)))
        }
    }

    suspend fun select(): SettingHome? {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            settingHomeDao.find()
        }
    }

    suspend fun delete(homeId: Long) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            settingHomeDao.delete(homeId)
        }
    }
}
