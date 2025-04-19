package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.platform.PlatformUtility
import dev.rivu.golpoai.platform.getContext
import dev.rivu.golpoai.platform.shareStory
import dev.rivu.golpoai.ui.components.StoryContentSection
import dev.rivu.golpoai.ui.components.StoryHeaderBar
import dev.rivu.golpoai.ui.theme.GolpoAITheme

data class StoryDetailScreen(val story: SavedStory) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val contextWrapper = PlatformUtility.getContext()

        GolpoAITheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    StoryHeaderBar(
                        onBack = { navigator.pop() },
                        actions = {
                            IconButton(onClick = {
                                PlatformUtility.shareStory(contextWrapper, story.story)
                            }) {
                                Icon(Icons.Default.Share, contentDescription = "Share")
                            }
                        }
                    )

                    StoryContentSection(
                        title = "Story",
                        prompt = story.prompt,
                        genre = story.genre,
                        story = story.story,
                        modifier = Modifier.padding(16.dp).fillMaxSize()
                    )
                }
            }
        }
    }
}
