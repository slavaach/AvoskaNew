package ru.yandex.slavaach.nullapplicatoin.features.reference.data

interface ReferenceUseCase {

    suspend fun addHome(id: Long, name: String, order: Int)

    suspend fun selectHome(): List<ReferenceDt>

    suspend fun deletHome(homeId: Long)
}
