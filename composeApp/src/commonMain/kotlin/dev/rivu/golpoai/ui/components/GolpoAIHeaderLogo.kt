package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import golpoai.composeapp.generated.resources.Res
import golpoai.composeapp.generated.resources.golpoai_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GolpoAIHeaderLogo(modifier: Modifier = Modifier) {
    val painter = painterResource(Res.drawable.golpoai_logo)
    Image(
        painter = painter,
        contentDescription = "GolpoAI Logo",
        modifier = modifier
            .height(72.dp)
            .padding(bottom = 8.dp)
    )
}
