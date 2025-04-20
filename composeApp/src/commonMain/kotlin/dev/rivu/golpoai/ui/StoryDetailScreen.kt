package dev.rivu.golpoai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import dev.rivu.golpoai.ui.components.StoryMetadata

data class StoryDetailScreen(val story: SavedStory) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val contextWrapper = PlatformUtility.getContext()


        Column(modifier = Modifier.fillMaxSize()) {
            StoryHeaderBar(
                onBack = { navigator.pop() },
                actions = {
                    IconButton(onClick = {
                        PlatformUtility.shareStory(contextWrapper, story.story.storyText)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    StoryMetadata(
                        metadata = story.story.storyMetadata, // assuming this is a formatted string
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                }

                item {
                    StoryContentSection(
                        story = story.story.storyText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }

}
