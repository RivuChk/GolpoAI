package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.data.models.StoryMetadata
import dev.rivu.golpoai.platform.PlatformUtility
import dev.rivu.golpoai.platform.getContext
import dev.rivu.golpoai.platform.shareStory
import dev.rivu.golpoai.presentation.StoryScreenModel
import dev.rivu.golpoai.ui.components.GolpoButton
import dev.rivu.golpoai.ui.components.StoryContentSection
import dev.rivu.golpoai.ui.components.StoryHeaderBar
import dev.rivu.golpoai.ui.components.StoryMetadata
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


        val listState = rememberLazyListState()
        val isScrolled by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 20 }
        }

        Scaffold(
            topBar = {
                StoryHeaderBar(
                    modifier = Modifier.fillMaxWidth(),
                    onBack = {
                        navigator.pop()
                    }
                )
            },
            bottomBar = {
                if (state.story != null && !state.loading) {
                    BottomAppBar(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding( horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                if (!state.saved) {
                                    GolpoButton(
                                        text = "Save",
                                        icon = Icons.Default.Add,
                                        onClick = {
                                            screenModel.saveStory(
                                                story = state.story!!
                                            )
                                        },
                                        enabled = !state.saved
                                    )
                                }

                                GolpoButton(
                                    text = "Share",
                                    icon = Icons.Default.Share,
                                    onClick = { PlatformUtility.shareStory(contextWrapper, state.story!!.storyText) }
                                )

                                GolpoButton(
                                    text = "Regenerate",
                                    icon = Icons.Default.Refresh,
                                    onClick = {
                                        screenModel.generateStory(prompt, genre, "English")
                                    }
                                )
                            }
                        }
                    )
                }
            }
        ) {
            when {
                state.loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.story != null -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        item {
                            StoryMetadata(
                                metadata = state.story!!.storyMetadata, // assuming this is a formatted string
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            )
                        }

                        item {
                            StoryContentSection(
                                story = state.story!!.storyText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                            )
                        }
                    }
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = state.error ?: "Unable to load story! :(",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.padding(16.dp)
                            )
                            GolpoButton(text = "Retry") {
                                screenModel.generateStory(prompt, genre, "English")
                            }
                        }
                    }
                }
            }
        }


    }
}

