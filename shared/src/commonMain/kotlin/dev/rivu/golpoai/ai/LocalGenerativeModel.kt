package dev.rivu.golpoai.ai

import dev.rivu.genai.kmpllminterferencelib.TextGenerator
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class LocalGenerativeModel(
    private val textGenerator: TextGenerator
) : GenerativeModel {

    override val isReady: StateFlow<Boolean> = textGenerator.isReady


    override suspend fun generateStory(prompt: String, awaitReadiness: Boolean): Result<String> {
        return try {
            if (!isReady.value && awaitReadiness) {
                isReady.first { it }
            }
            Result.success(textGenerator.generate(prompt))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
