package ru.yandex.slavaach.nullapplicatoin.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.yandex.slavaach.nullapplicatoin.R

@Composable
fun CustomCheckbox(
    modifier: Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    IconButton(modifier = modifier, onClick = { onCheckedChange(!checked) }) {
        // the box image frame unchecked
        Icon(
            painter = painterResource(id = R.drawable.kv),
            contentDescription = "Unchecked",
            tint = MaterialTheme.colors.secondary,
        )
        AnimatedVisibility(
            modifier = modifier,
            visible = checked,
            exit = shrinkOut(shrinkTowards = Alignment.TopStart) + fadeOut()
        ) {
            // the check only (without the surrounding box)
            Icon(
                painter = painterResource(id = R.drawable.check2),
                contentDescription = "Checked",
                tint = MaterialTheme.colors.secondaryVariant,
            )
        }
    }
}
