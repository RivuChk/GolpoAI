package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import dev.rivu.golpoai.PlatformEnum
import dev.rivu.golpoai.getPlatform
import dev.rivu.golpoai.presentation.StoryScreenModel
import dev.rivu.golpoai.ui.components.GolpoAIHeaderLogo
import dev.rivu.golpoai.ui.components.GolpoButton
import dev.rivu.golpoai.ui.components.GolpoDropdownMenu
import dev.rivu.golpoai.ui.components.GolpoTextField
import dev.rivu.golpoai.ui.components.LocalGenerationToggle
import dev.rivu.golpoai.ui.theme.KotlinRed
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


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GolpoAIHeaderLogo(modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.width(8.dp))
                Text(
                    "GolpoAI",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(Modifier.height(24.dp))

            GolpoTextField(value = prompt, onValueChange = { prompt = it }, label = "Enter story prompt")

            Spacer(Modifier.height(16.dp))

            GolpoDropdownMenu(selectedGenre = genre, onGenreSelected = { genre = it })

            Spacer(Modifier.height(16.dp))

            if (getPlatform().platform == PlatformEnum.Android) {
                LocalGenerationToggle(isEnabled = useLocal.value, isReady = isOfflineModelReady.value) {
                    screenModel.setUseLocalGeneration(it)
                }

                if (!isOfflineModelReady.value) {
                    Text(
                        "⏳ Offline model is not ready yet. We can generate online",
                        style = MaterialTheme.typography.caption,
                        color = KotlinRed
                    )
                }

                Spacer(Modifier.height(32.dp))
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                GolpoButton(
                    text = "Generate Story",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    enabled = prompt.isNotBlank() && (isOfflineModelReady.value || !useLocal.value)
                ) {
                    navigator.push(StoryScreen(prompt, genre))
                }

                GolpoButton(
                    text = "View Saved Stories",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        navigator.push(HistoryScreen)
                    },
                )
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen.Content()
}

