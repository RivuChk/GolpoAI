package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.rivu.golpoai.ui.theme.KotlinRed

@Composable
fun GolpoButton(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    bgColor: androidx.compose.ui.graphics.Color = KotlinRed,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = bgColor),
        enabled = enabled
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(ButtonDefaults.IconSize)
            )
        }
        Text(text)
    }
}
