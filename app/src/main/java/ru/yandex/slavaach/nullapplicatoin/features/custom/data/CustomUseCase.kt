package ru.yandex.slavaach.nullapplicatoin.features.custom.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomUseCase(private val customDao: CustomDao,) {
    suspend fun add(custom: Custom) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            customDao.add(listOf(custom))
        }
    }

    suspend fun deleteBuy() {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            customDao.deleteBuy()
        }
    }

    suspend fun select(home: Long): List<CustomWithText> {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            customDao.findAllWithText(home).sortedBy { it.id }
        }
    }

    suspend fun select(home: List<Long>): List<CustomWithText> {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            customDao.findAllWithText(home).sortedBy { it.id }
        }
    }

    suspend fun delete(homeId: Long) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            customDao.delete(homeId)
        }
    }
}
