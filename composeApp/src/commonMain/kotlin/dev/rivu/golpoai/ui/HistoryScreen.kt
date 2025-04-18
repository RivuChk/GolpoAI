package dev.rivu.golpoai.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rivu.golpoai.presentation.HistoryScreenModel
import dev.rivu.golpoai.ui.components.GolpoAIHeaderLogo
import dev.rivu.golpoai.ui.theme.GolpoAITheme

object HistoryScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<HistoryScreenModel>()
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        GolpoAITheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(modifier= Modifier.fillMaxWidth()) {
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
                    }
                    Text("Past Stories", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        items(state.stories) { story ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        navigator.push(StoryDetailScreen(story))
                                    }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Prompt: ${story.prompt}", style = MaterialTheme.typography.subtitle1)
                                    Text("Genre: ${story.genre}", style = MaterialTheme.typography.body2)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        story.story.take(200) + "...",
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
