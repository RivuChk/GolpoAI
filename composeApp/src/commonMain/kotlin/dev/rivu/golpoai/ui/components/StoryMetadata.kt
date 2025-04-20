package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rivu.golpoai.data.models.StoryMetadata


@Composable
fun StoryMetadata(
    metadata: StoryMetadata,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 8.dp)) {
        Text(
            text = "Title: ${metadata.title}",
            style = MaterialTheme.typography.caption
        )
        Text(
            text = "Prompt: ${metadata.prompt}",
            style = MaterialTheme.typography.caption
        )
        Text(
            text = "Genre: ${metadata.genre}",
            style = MaterialTheme.typography.caption
        )
        Text(
            text = "Language: ${metadata.language}",
            style = MaterialTheme.typography.caption
        )
        Text(
            text = "Mode: ${if (metadata.isOffline) "Offline" else "Online"}",
            style = MaterialTheme.typography.caption
        )
    }
}
