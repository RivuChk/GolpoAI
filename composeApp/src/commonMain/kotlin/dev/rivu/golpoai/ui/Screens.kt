package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.ui.components.GolpoButton
import dev.rivu.golpoai.ui.components.GolpoDropdownMenu
import dev.rivu.golpoai.ui.components.GolpoTextField
import dev.rivu.golpoai.ui.theme.GolpoAITheme

// ------------ Screens ------------
object HomeScreen : Screen {
    @Composable
    override fun Content() {
        var prompt by remember { mutableStateOf("") }
        var genre by remember { mutableStateOf("Sci-fi") }
        val navigator = LocalNavigator.currentOrThrow

        GolpoAITheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("GolpoAI", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(16.dp))
                    GolpoTextField(value = prompt, onValueChange = { prompt = it }, label = "Enter story prompt")
                    Spacer(modifier = Modifier.height(12.dp))
                    GolpoDropdownMenu(selectedGenre = genre, onGenreSelected = { genre = it })
                    Spacer(modifier = Modifier.height(12.dp))
                    GolpoButton(text = "Generate Story") {
                        navigator.push(StoryScreen(prompt, genre))
                    }
                }
            }
        }
    }
}

data class StoryScreen(val prompt: String, val genre: String) : Screen {
    @Composable
    override fun Content() {
        GolpoAITheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Your Story", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Prompt: $prompt\nGenre: $genre", style = MaterialTheme.typography.body2)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Once upon a time in a $genre world...", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}
