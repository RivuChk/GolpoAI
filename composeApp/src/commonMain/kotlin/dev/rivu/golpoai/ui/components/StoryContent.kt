package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StoryContent(
    modifier: Modifier = Modifier,
    story: String,
    scrollState:ScrollState = rememberScrollState()
) {
    var scale by remember { mutableStateOf(1f) }
    val transformableState = rememberTransformableState { zoomChange, _, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 3f) // Limit the zoom scale between 1x and 3x
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .transformable(state = transformableState)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = story,
            fontSize = (18.sp * scale),
            lineHeight = (28.sp * scale),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify,
        )
    }
}
