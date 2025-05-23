package dev.rivu.golpoai

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import composeAppModule
import dev.rivu.golpoai.ui.HomeScreen
import dev.rivu.golpoai.ui.components.GolpoButton
import dev.rivu.golpoai.ui.theme.GolpoAITheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import golpoai.composeapp.generated.resources.Res
import golpoai.composeapp.generated.resources.compose_multiplatform
import org.koin.core.logger.Level
import org.koin.mp.KoinPlatform.startKoin

@Composable
@Preview
fun App() {
    GolpoAITheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Navigator(HomeScreen)
        }
    }
}
