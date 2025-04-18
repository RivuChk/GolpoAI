package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.platform.PlatformUtility
import dev.rivu.golpoai.platform.getContext
import dev.rivu.golpoai.platform.shareStory
import dev.rivu.golpoai.ui.components.GolpoAIHeaderLogo
import dev.rivu.golpoai.ui.theme.GolpoAITheme

data class StoryDetailScreen(val story: SavedStory) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val contextWrapper = PlatformUtility.getContext()

        GolpoAITheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
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


                        IconButton(
                            onClick = {
                                PlatformUtility.shareStory(contextWrapper = contextWrapper, story = story.story)
                            },
                            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share"
                            )
                        }
                    }
                    Text("Story", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Text("Prompt: ${story.prompt}", style = MaterialTheme.typography.subtitle1)
                        Text("Genre: ${story.genre}", style = MaterialTheme.typography.body2)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(story.story, style = MaterialTheme.typography.body1)

                    }
                }
            }
        }
    }
}
