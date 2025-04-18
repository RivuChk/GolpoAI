package dev.rivu.golpoai.ai

import kotlinx.coroutines.flow.StateFlow

interface GenerativeModel {
    suspend fun generateStory(prompt: String, awaitReadiness: Boolean = false): Result<String>
    val isReady: StateFlow<Boolean>
}
