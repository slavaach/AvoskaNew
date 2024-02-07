package ru.yandex.slavaach.nullapplicatoin.core.presentation.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SubTitleNameSource {

    val eventChannel = MutableSharedFlow<SetSubTitleName>(replay = 3)

    fun observeTransferEvent(): Flow<SetSubTitleName> {
        return eventChannel
    }

    suspend fun emitEvent(event: SetSubTitleName) {
        eventChannel.emit(event)
    }
}

data class SetSubTitleName(val name: String)
