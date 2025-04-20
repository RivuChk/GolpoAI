package dev.rivu.golpoai.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.data.models.SavedStory
import dev.rivu.golpoai.platform.PlatformUtility
import dev.rivu.golpoai.platform.getContext
import dev.rivu.golpoai.platform.shareStory
import dev.rivu.golpoai.ui.components.StoryContent
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

            Column(
                modifier = Modifier.fillMaxSize().padding(PaddingValues(horizontal = 16.dp, vertical = 12.dp))
            ) {
                val scrollState = rememberScrollState()

                val showMetadata by remember {
                    derivedStateOf { scrollState.value <= 20 }
                }
                AnimatedVisibility(
                    visible = showMetadata,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    StoryMetadata(
                        metadata = story.story.storyMetadata, // assuming this is a formatted string
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                }
                StoryContent(
                    story = story.story.storyText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .weight(1f),
                    scrollState = scrollState
                )
            }
        }
    }

}
