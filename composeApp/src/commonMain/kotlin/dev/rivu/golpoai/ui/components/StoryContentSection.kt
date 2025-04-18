package dev.rivu.golpoai.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rivu.golpoai.logging.Logger

@Composable
fun StoryContentSection(
    title: String,
    prompt: String,
    genre: String,
    story: String?,
    loading: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(title, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Prompt: $prompt", style = MaterialTheme.typography.body2)
        Text("Genre: $genre", style = MaterialTheme.typography.body2)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            loading -> {
                CircularProgressIndicator()
            }

            error != null -> {
                Logger.e("Error: $error")
                Text("Error: $error", color = MaterialTheme.colors.error)
            }

            story != null -> {
                Text(story, style = MaterialTheme.typography.body1)
            }

            else -> {
                Text("No story available.")
            }
        }
    }
}
