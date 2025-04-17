package dev.rivu.golpoai.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.*
import androidx.compose.ui.text.TextStyle

val KotlinRed = Color(0xFFF74C00)
val KotlinBlue = Color(0xFF0069CA)
val BackgroundDark = Color(0xFF0D1117)
val SurfaceDark = Color(0xFF1A1F24)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFB0B0B0)

private val golpoDarkColors = darkColors(
    primary = KotlinBlue,
    primaryVariant = KotlinBlue,
    secondary = KotlinRed,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun GolpoAITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = golpoDarkColors,
        typography = Typography(
            h4 = TextStyle(color = TextPrimary),
            body1 = TextStyle(color = TextPrimary),
            body2 = TextStyle(color = TextSecondary),
            button = TextStyle(color = Color.White)
        ),
        shapes = Shapes(),
        content = content
    )
}
