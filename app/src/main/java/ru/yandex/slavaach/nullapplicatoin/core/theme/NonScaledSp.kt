package ru.yandex.slavaach.nullapplicatoin.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp

val Int.scaledSp
    @Composable
    get() = (this * LocalDensity.current.fontScale).dp
