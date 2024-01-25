package ru.yandex.slavaach.nullapplicatoin.core.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.pulsateClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idel) }

    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.7f else 1f)
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState == if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idel
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
        .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { }
}

enum class ButtonState { Pressed, Idel }
