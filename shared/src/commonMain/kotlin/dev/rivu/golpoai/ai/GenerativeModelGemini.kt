package dev.rivu.golpoai.ai

import dev.rivu.golpoai.logging.Logger
import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel as GeminiApiGenerativeModel

class GenerativeModelGemini(private val apiKey: String) : GenerativeModel {
    private val model by lazy {
        GeminiApiGenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = apiKey
        )
    }

    override suspend fun generateStory(prompt: String): Result<String> {
        return try {
            val input = content { text(prompt) }
            val response = model.generateContent(input)
            Logger.d("Generated story: $response")
            response.text?.let {
                Result.success(it)
            } ?: throw UnsupportedOperationException("No story generated.")
        } catch (e: Exception) {
            Logger.e("Error generating story", e)
            Result.failure(e)
        }
    }
}
