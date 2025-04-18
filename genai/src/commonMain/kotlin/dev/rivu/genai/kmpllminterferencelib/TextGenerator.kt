package dev.rivu.genai.kmpllminterferencelib

import kotlinx.coroutines.flow.StateFlow

expect class TextGenerator {
    suspend fun generate(prompt: String): String
    val isReady: StateFlow<Boolean>
}
