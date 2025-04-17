package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import dev.rivu.golpoai.logging.Logger
import dev.rivu.golpoai.presentation.StoryScreenModel
import dev.rivu.golpoai.ui.components.GolpoAIHeaderLogo
import dev.rivu.golpoai.ui.theme.GolpoAITheme

data class StoryScreen(val prompt: String, val genre: String) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<StoryScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        // Trigger story generation only once
        LaunchedEffect(Unit) {
            screenModel.generateStory(prompt, genre, "English")
        }

        GolpoAITheme {
            Surface(modifier = Modifier.Companion.fillMaxSize()) {
                Column(
                    modifier = Modifier.Companion
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { navigator.pop() },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        GolpoAIHeaderLogo(modifier = Modifier.align(Alignment.Center))

                        // Regenerate button (right)
                        if (state.story != null || state.error != null) {
                            IconButton(
                                onClick = {
                                    screenModel.generateStory(prompt, genre, "English")
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Regenerate"
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
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
}
