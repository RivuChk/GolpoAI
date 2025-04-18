package dev.rivu.genai.kmpllminterferencelib

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class TextGenerator {
    actual val isReady: StateFlow<Boolean> = MutableStateFlow(false).asStateFlow()

    actual suspend fun generate(prompt: String): String {
        return "Not implemented for JVM yet"
    }
}
