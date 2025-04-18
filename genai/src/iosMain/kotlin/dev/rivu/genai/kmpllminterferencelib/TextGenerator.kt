package dev.rivu.genai.kmpllminterferencelib

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


actual class TextGenerator {

    actual val isReady: StateFlow<Boolean> = MutableStateFlow(false).asStateFlow()

    actual suspend fun generate(prompt: String): String  {
        TODO("Text generation on iOS is not yet implemented")
    }
}
