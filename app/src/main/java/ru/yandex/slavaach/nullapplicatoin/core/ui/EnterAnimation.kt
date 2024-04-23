package ru.yandex.slavaach.nullapplicatoin.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -40 },
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        ) + expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(durationMillis = 300, easing = LinearEasing)
        ) + fadeIn(initialAlpha = 3f, animationSpec = tween(durationMillis = 3000, easing = LinearEasing)),
        exit = slideOutVertically(
            targetOffsetY = { -40 },
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        ) + shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        ) + fadeOut(targetAlpha = 3f),
    ) {
        content()
    }
}
