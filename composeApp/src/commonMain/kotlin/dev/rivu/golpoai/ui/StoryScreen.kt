package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.platform.PlatformUtility
import dev.rivu.golpoai.platform.generateUUID
import dev.rivu.golpoai.platform.getContext
import dev.rivu.golpoai.platform.shareStory
import dev.rivu.golpoai.presentation.StoryScreenModel
import dev.rivu.golpoai.ui.components.GolpoButton
import dev.rivu.golpoai.ui.components.StoryContentSection
import dev.rivu.golpoai.ui.components.StoryHeaderBar
import dev.rivu.golpoai.ui.theme.GolpoAITheme
import kotlinx.datetime.Clock
import kotlin.time.ExperimentalTime

data class StoryScreen(val prompt: String, val genre: String) : Screen {
    @OptIn(ExperimentalTime::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<StoryScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        val contextWrapper = PlatformUtility.getContext()

        // Trigger story generation only once
        LaunchedEffect(Unit) {
            screenModel.generateStory(prompt, genre, "English")
        }

        GolpoAITheme {
            Surface(modifier = Modifier.Companion.fillMaxSize()) {
                Column {
                    StoryHeaderBar(
                        onBack = { navigator.pop() },
                        actions = {
                            if (state.story != null) {
                                IconButton(onClick = {
                                    PlatformUtility.shareStory(contextWrapper, state.story!!)
                                }) {
                                    Icon(Icons.Default.Share, contentDescription = "Share")
                                }
                            }

                            if (state.story != null || state.error != null) {
                                IconButton(onClick = {
                                    screenModel.generateStory(prompt, genre, "English")
                                }) {
                                    Icon(Icons.Filled.Refresh, contentDescription = "Regenerate")
                                }
                            }
                        }
                    )
                    Column(
                        modifier = Modifier.Companion
                            .padding(16.dp)
                    ) {

                        if (state.story != null && !state.saved) {
                            GolpoButton(
                                text = "Save Story",
                                icon = Icons.Filled.AddCircle,
                                onClick = {
                                    val id = PlatformUtility.generateUUID()
                                    val timestamp = Clock.System.now().toEpochMilliseconds()
                                    screenModel.saveStory(
                                        SavedStory(id, prompt, genre, state.story!!, timestamp)
                                    )
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 8.dp)
                            )
                        }
                        StoryContentSection(
                            title = "Your Story",
                            prompt = prompt,
                            genre = genre,
                            story = state.story,
                            error = state.error,
                            loading = state.loading,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
