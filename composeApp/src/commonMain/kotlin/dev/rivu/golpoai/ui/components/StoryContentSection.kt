package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StoryContentSection(
    modifier: Modifier = Modifier,
    story: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = story,
            style = MaterialTheme.typography.body1.copy(
                fontSize = 20.sp,
                lineHeight = 28.sp
            ),
            textAlign = TextAlign.Justify
        )
    }
}
