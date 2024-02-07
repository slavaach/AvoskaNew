package ru.yandex.slavaach.nullapplicatoin.core.presentation.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TitleNameSource {

    val eventChannel = MutableSharedFlow<TitleName>(replay = 1)

    fun observeTransferEvent(): Flow<TitleName> {
        return eventChannel
    }

    suspend fun emitEvent(event: TitleName) {
        eventChannel.emit(event)
    }
}

sealed class TitleName {

    class SetTitleNameHome : TitleName()

    class SetTitleNameMulti : TitleName()

    data class SetTitleName(val name: String) : TitleName()
}
