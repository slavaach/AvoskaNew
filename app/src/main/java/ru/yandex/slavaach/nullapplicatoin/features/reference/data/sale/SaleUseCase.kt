package ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceDt
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.ReferenceUseCase
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.Home

class SaleUseCase(private val saleDao: SaleDao,) : ReferenceUseCase {
    override suspend fun addHome(id: Long, name: String, order: Int) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            saleDao.addHomes(listOf(Sale(id, name, order)))
        }
    }

    override suspend fun selectHome(): List<ReferenceDt> {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            saleDao.findAllHomes().sortedBy { it.id } ?: listOf<Home>()
        }
    }

    override suspend fun deletHome(homeId: Long) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            saleDao.deleteHome(homeId)
        }
    }
}
