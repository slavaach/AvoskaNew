package ru.yandex.slavaach.nullapplicatoin.features.reference.data.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceDt
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceUseCase

class HomeUseCase(
    private val homeDao: HomeDao,
) : ReferenceUseCase {
    override suspend fun addHome(id: Long, name: String, order: Int) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            homeDao.addHomes(listOf(Home(id, name, order)))
        }
    }

    override suspend fun selectHome(): List<ReferenceDt> {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            homeDao.findAllHomes().sortedBy { it.id } ?: listOf<Home>()
        }
    }

    override suspend fun deletHome(homeId: Long) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            homeDao.deleteHome(homeId)
        }
    }
}
