package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import dev.rivu.golpoai.logging.Logger
import dev.rivu.golpoai.presentation.StoryScreenModel
import dev.rivu.golpoai.ui.theme.GolpoAITheme

data class StoryScreen(val prompt: String, val genre: String) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<StoryScreenModel>()
        val state by screenModel.state.collectAsState()

        // Trigger story generation only once
        LaunchedEffect(Unit) {
            screenModel.generateStory(prompt, genre, "English")
        }

        GolpoAITheme {
            Surface(modifier = Modifier.Companion.fillMaxSize()) {
                Column(
                    modifier = Modifier.Companion
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Your Story", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.Companion.height(16.dp))

                    Text("Prompt: $prompt", style = MaterialTheme.typography.body2)
                    Text("Genre: $genre", style = MaterialTheme.typography.body2)

                    Spacer(modifier = Modifier.Companion.height(16.dp))

                    when {
                        state.loading -> {
                            CircularProgressIndicator()
                        }

                        state.error != null -> {
                            Logger.e("Error: ${state.error}")
                            Text("Error: ${state.error}", color = MaterialTheme.colors.error)
                        }

                        state.story != null -> {
                            Text(state.story!!, style = MaterialTheme.typography.body1)
                        }

                        else -> {
                            Text("No story available.")
                        }
                    }
                }
            }
        }
    }
}
