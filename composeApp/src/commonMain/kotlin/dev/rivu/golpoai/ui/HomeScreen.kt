package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.Platform
import dev.rivu.golpoai.PlatformEnum
import dev.rivu.golpoai.getPlatform
import dev.rivu.golpoai.presentation.HistoryScreenModel
import dev.rivu.golpoai.presentation.StoryScreenModel
import dev.rivu.golpoai.ui.components.GolpoAIHeaderLogo
import dev.rivu.golpoai.ui.components.GolpoButton
import dev.rivu.golpoai.ui.components.GolpoDropdownMenu
import dev.rivu.golpoai.ui.components.GolpoTextField
import dev.rivu.golpoai.ui.components.LocalGenerationToggle
import dev.rivu.golpoai.ui.theme.GolpoAITheme
import org.jetbrains.compose.ui.tooling.preview.Preview

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        var prompt by rememberSaveable { mutableStateOf("") }
        var genre by rememberSaveable { mutableStateOf("Sci-fi") }
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<StoryScreenModel>()
        val useLocal = screenModel.useLocalGeneration.collectAsState()
        val isOfflineModelReady = screenModel.isOfflineModelReady.collectAsState()

        GolpoAITheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        GolpoAIHeaderLogo(modifier = Modifier.align(Alignment.CenterVertically))
                        Text("GolpoAI", style = MaterialTheme.typography.h2, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    GolpoTextField(value = prompt, onValueChange = { prompt = it }, label = "Enter story prompt")
                    Spacer(modifier = Modifier.height(12.dp))
                    GolpoDropdownMenu(selectedGenre = genre, onGenreSelected = { genre = it })
                    Spacer(modifier = Modifier.height(12.dp))
                    GolpoButton(
                        text = "Generate Story",
                        enabled = prompt.isNotBlank() && (isOfflineModelReady.value || !useLocal.value)
                    ) {
                        navigator.push(StoryScreen(prompt, genre))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    GolpoButton(
                        text = "View Past Stories",
                        onClick = {
                            navigator.push(HistoryScreen)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp),
                    )

                    if (getPlatform().platform == PlatformEnum.Android) {
                        // Platform-specific
                        LocalGenerationToggle(isEnabled = useLocal.value) {
                            screenModel.setUseLocalGeneration(it)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen.Content()
}

