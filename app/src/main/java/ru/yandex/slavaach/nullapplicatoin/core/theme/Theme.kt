package ru.yandex.slavaach.nullapplicatoin.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import ru.yandex.slavaach.nullapplicatoin.R

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = Purple700,
    secondary = TextDark,
    surface = brownDark,
    secondaryVariant = checkDark,

)

private val LightColorPalette = lightColors(
    primary = PrimaryLite,
    primaryVariant = Purple700,
    secondary = TextLite,
    surface = brownLite,
    secondaryVariant = checkLite,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

enum class ListIconDarkLite {
    IC_LAUNCHER
}

val DarkIconPalette = hashMapOf<ListIconDarkLite, Int>(
    ListIconDarkLite.IC_LAUNCHER to R.drawable.ic_launcher_dark
)

val LiteIconPalette = hashMapOf<ListIconDarkLite, Int>(
    ListIconDarkLite.IC_LAUNCHER to R.drawable.ic_launcher
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
