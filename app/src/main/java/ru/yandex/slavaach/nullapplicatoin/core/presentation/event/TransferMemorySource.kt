package ru.yandex.slavaach.nullapplicatoin.core.presentation.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TransferMemorySource {

    val eventChannel = MutableSharedFlow<Transfer>(replay = 1)

    fun observeTransferEvent(): Flow<Transfer> {
        return eventChannel
    }

    suspend fun emitEvent(event: Transfer) {
        eventChannel.emit(event)
    }
}

sealed class Transfer {

    class UpdateSale : Transfer()

    class UpdateHome : Transfer()

    class UpdateCustom : Transfer()

    class UpdateBuy : Transfer()

    data class UpdateHomeSelectCustom(val id: Long) : Transfer()

    data class UpdateHomeSelectBuy(val id: List<Long>) : Transfer()
}
