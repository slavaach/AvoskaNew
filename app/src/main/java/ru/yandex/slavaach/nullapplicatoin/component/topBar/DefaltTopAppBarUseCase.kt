package ru.yandex.slavaach.nullapplicatoin.component.topBar

import kotlinx.coroutines.flow.Flow
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.ClickOnTitleSource
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.IconTitleSource
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetClickOnTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetIconTitle
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SetSubTitleName
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.SubTitleNameSource
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.TitleName
import ru.yandex.slavaach.nullapplicatoin.core.presentation.event.TitleNameSource

class DefaltTopAppBarUseCase(
    private val titleNameSource: TitleNameSource,
    private val clickOnTitleSource: ClickOnTitleSource,
    private val subTitleNameSource: SubTitleNameSource,
    private val iconTitleSource: IconTitleSource,
) {
    fun observeTitleNameSource(): Flow<TitleName> {
        return titleNameSource.observeTransferEvent()
    }

    suspend fun emitEventTitleNameSource(event: TitleName) {
        titleNameSource.emitEvent(event)
    }

    fun observeClickOnTitleSource(): Flow<SetClickOnTitle> {
        return clickOnTitleSource.observeTransferEvent()
    }

    suspend fun emitEventClickOnTitleSource(event: SetClickOnTitle) {
        clickOnTitleSource.emitEvent(event)
    }

    fun observeSubTitleNameSource(): Flow<SetSubTitleName> {
        return subTitleNameSource.observeTransferEvent()
    }

    suspend fun emitEventSubTitleNameSource(event: SetSubTitleName) {
        subTitleNameSource.emitEvent(event)
    }

    fun observeIconTitleSource(): Flow<SetIconTitle> {
        return iconTitleSource.observeTransferEvent()
    }

    suspend fun emitEventIconTitleSource(event: SetIconTitle) {
        iconTitleSource.emitEvent(event)
    }
}
