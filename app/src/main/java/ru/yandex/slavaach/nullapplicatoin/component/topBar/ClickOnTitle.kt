package ru.yandex.slavaach.nullapplicatoin.component.topBar

enum class ClickOnTitle(val onClicing: Boolean, val padding: Int) {
    NOT_CLICK(false, 0),
    CLICK_ONE(true, 0),
    CLICK_MULTI(true, 1)
}
