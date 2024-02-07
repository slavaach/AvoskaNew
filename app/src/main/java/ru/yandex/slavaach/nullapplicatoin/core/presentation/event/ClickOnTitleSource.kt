package ru.yandex.slavaach.nullapplicatoin.core.presentation.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.yandex.slavaach.nullapplicatoin.component.topBar.ClickOnTitle

class ClickOnTitleSource {

    val eventChannel = MutableSharedFlow<SetClickOnTitle>(replay = 1)

    fun observeTransferEvent(): Flow<SetClickOnTitle> {
        return eventChannel
    }

    suspend fun emitEvent(event: SetClickOnTitle) {
        eventChannel.emit(event)
    }
}

data class SetClickOnTitle(val it: ClickOnTitle)
