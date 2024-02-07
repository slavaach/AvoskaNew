package ru.yandex.slavaach.nullapplicatoin.core.presentation.event

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class IconTitleSource {

    val eventChannel = MutableSharedFlow<SetIconTitle>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun observeTransferEvent(): Flow<SetIconTitle> {
        return eventChannel
    }

    suspend fun emitEvent(event: SetIconTitle) {
        eventChannel.emit(event)
    }
}

data class SetIconTitle(val icon: Int)
